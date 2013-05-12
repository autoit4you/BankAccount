package me.autoit4you.bankaccount;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.MetricsLite;

	public class BankAccount extends JavaPlugin{
		//variables
	    private static final Logger log = Logger.getLogger("Minecraft");
	    public Database db = null;
	    public Vault vault = null;
	    public Permissions perm = null;
    	private String dbserver = null;
    	private String dbdatabase = null;
    	private String dbuser = null;
    	private String dbpwd = null;
    	
	    @Override
	    public void onDisable() {
	        saveConfig();
	    	log.info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
	    }

	    @Override
	    public void onEnable() {
	    	//loading or creating config.yml
	    	saveDefaultConfig();
	    	dbserver = getConfig().getString("Server");
	    	dbdatabase = getConfig().getString("Database");
	    	dbuser = getConfig().getString("Username");
	    	dbpwd = getConfig().getString("Password");
	    	//connecting to database
	    	try{
	    	db = new Database(dbserver, dbdatabase, dbuser, dbpwd);
	    	} catch (Exception e)
	    	{
	    		e.printStackTrace();
	    		log.severe(String.format("Cannot connect with MySQL. Server offline or database does not exist. Disabling..."));
	    		getServer().getPluginManager().disablePlugin(this);
	    		return;
	    	}
	    	//Register usage of an economy plugin
	    	vault = new Vault();
	    	if(!vault.setupEconomy()){
	    		log.severe(String.format("Cannot setup economy system. Disabling..."));
	    		getServer().getPluginManager().disablePlugin(this);
	    		return;
	    	}
	    	//Register usage of a permission plugin
	    	perm = new Permissions();
	    	if(!perm.setupPermissions()){
	    		log.severe(String.format("Cannot setup permissions system. Disabling..."));
	    		getServer().getPluginManager().disablePlugin(this);
	    		return;
	    	}
	    	//Enable metrics
	    	try{
	    		MetricsLite metrics = new MetricsLite(this);
	    		metrics.start();
	    	}catch(IOException e){
	    		log.warning("Could not enable metrics!");
	    	}
	    	log.info(String.format("[%s] Enabled Version %s", getDescription().getName(), getDescription().getVersion()));
	    }
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) throws CommandException{
		try{
			if(!(sender instanceof Player)){
				sender.sendMessage("You must be a player to use BankAccount!");
				return false;
			}else if(cmd.getName().equalsIgnoreCase("account")){
				if((args.length < 1 || args[0].equalsIgnoreCase("help")) && perm.user(sender, args)){
					sender.sendMessage(ChatColor.GOLD + "BankAccount Help Page:");
					sender.sendMessage(ChatColor.YELLOW + "/account help - Shows this help");
					sender.sendMessage(ChatColor.YELLOW + "/account open <name> - Opens a new account");
					sender.sendMessage(ChatColor.YELLOW + "/account close <name> - Closes a account");
					sender.sendMessage(ChatColor.YELLOW + "/account list - Lists all accounts you have access to");
					sender.sendMessage(ChatColor.YELLOW + "/account withdraw <name> <amount> - Withdraw money from a account you have access to");
					sender.sendMessage(ChatColor.YELLOW + "/account deposit <name> <amount> - Deposit money to a account you have access to");
					sender.sendMessage(ChatColor.YELLOW + "/account balance <name> - Shows the balance of the specified account");
					sender.sendMessage(ChatColor.YELLOW + "/account transfer <from> <to> <amount> - Transfer the specified value from account A to account B");
				} else if(args[0].equalsIgnoreCase("open") && args[1] != null && args.length > 0 && perm.user(sender, args)){
					if(args[1].length()> 250){
						sender.sendMessage(ChatColor.RED + "Accountname is too long!");
					}else if(db.existAccount(args[1].toString())){
						sender.sendMessage(ChatColor.RED + "That name is already used. Please use another one");
					}else if(db.createAccount(sender.getName(), args[1].toString())){
						log.info("[BankAccount] " + sender.getName() + " opened the bankaccount " + args[1].toString());
						sender.sendMessage(ChatColor.GREEN + "Account opened!");
					} else {
						log.info("[BankAccount] " + sender.getName() + " has problems with opening the account " + args.toString());
						sender.sendMessage(ChatColor.RED + "Error while opening your account. Check the console for more informations!");
					}
				}else if(args[0].equalsIgnoreCase("close") && args.length > 0 && perm.user(sender, args)){
					if(db.removeAccount(sender.getName(), args[1].toString())){
						log.info("[BankAccount] " + sender.getName() + " closed his access to the bankaccount " + args[1].toString());
						sender.sendMessage(ChatColor.GREEN + "Account closed!");
					} else {
						log.info("[BankAccount] " + sender.getName() + " has problems with closing the account " + args.toString());
						sender.sendMessage(ChatColor.RED + "Error while closing your account. Check the console for more informations!");
					}
				}else if(args[0].equalsIgnoreCase("list") && perm.user(sender, args)){
					String info = db.getAccounts(sender.getName());
					sender.sendMessage(info);
				}else if(args[0].equalsIgnoreCase("withdraw") && args.length > 1 && perm.user(sender, args)){
					if(args[1] == null || args[2] == null)
						throw new CommandException();
					if(db.getRights(args[1], sender.getName()) != 2){
						sender.sendMessage(ChatColor.RED + "You do not have permissions for that account!");
						return true;
					}
					if(db.checkmoney(args[1], Double.parseDouble(args[2]))){
						if(db.withdrawMoney(args[1], Double.parseDouble(args[2]))){
							vault.withdrawMoney(sender.getName(), Double.parseDouble(args[2]));
							sender.sendMessage(ChatColor.GOLD + "You withdraw $" + args[2] + " from the account '" + args[1] + "'");
							System.out.println(String.valueOf(vault.getMoney(sender.getName())) + " " + String.valueOf(db.getBalance(args[1])));
						}else{
							sender.sendMessage(ChatColor.RED + "An error occurred while withdrawing some money from a account.");
						}
					}else{
						sender.sendMessage(ChatColor.GOLD + "You don't have enough money on that account!");
					}
				}else if(args[0].equalsIgnoreCase("deposit") && args.length > 2 && perm.user(sender, args)){
					if(args[1] == null || args[2] == null)
						throw new CommandException();
					if(db.getRights(args[1], sender.getName()) != 2){
						sender.sendMessage(ChatColor.RED + "You do not have permissions for that account!");
						return true;
					}
					if(vault.hasMoney(sender.getName(), Double.parseDouble(args[2]))){
						if(db.depositMoney(args[1], Double.parseDouble(args[2]))){
							vault.depositMoney(sender.getName(), Double.parseDouble(args[2]));
							sender.sendMessage(ChatColor.GOLD + "You sent $" + args[2] + " to the account '" + args[1] + "'");
							System.out.println(String.valueOf(vault.getMoney(sender.getName())) + " " + String.valueOf(db.getBalance(args[1])));
						}else{
							sender.sendMessage(ChatColor.RED + "An error occurred while depositing some money to a account.");
						}
					}else{
						sender.sendMessage(ChatColor.GOLD + "You don't have enough money!");
					}
				}else if(args[0].equalsIgnoreCase("balance") && args.length > 0 && perm.user(sender, args)){
					if(args[1] == null)
						throw new CommandException();
					if(db.getRights(args[1], sender.getName()) != 2){
						sender.sendMessage(ChatColor.RED + "You do not have permissions for that account!");
						return true;
					}
					DecimalFormat df = new DecimalFormat(",##0.00");
					sender.sendMessage(ChatColor.GOLD + args[1] + ": " + df.format(db.getBalance(args[1])));
				}else if(args[0].equalsIgnoreCase("transfer") && args.length > 2 && perm.user(sender, args)){
					if(args[1] == null || args[2] == null || args[3] == null)
						throw new CommandException();
					if(db.getRights(args[1], sender.getName()) != 2){
						sender.sendMessage(ChatColor.RED + "You do not have permissions for that account!");
						return true;
					}
					if(!db.existAccount(args[1])){
						sender.sendMessage(ChatColor.RED + "The account which sends money does not exist.");
						return true;
					}else if(db.existAccount(args[2])){
						sender.sendMessage(ChatColor.RED + "The account which receives money does not exist.");
						return true;
					}else if(!db.checkmoney(args[1], Double.parseDouble(args[3]))){
						sender.sendMessage(ChatColor.RED + "The account which sends money does not have enough money.");
						return true;
					}else if(args[1] == args[2]){
						sender.sendMessage(ChatColor.YELLOW + "Please check your arguments. The receiver can't be the same than the sender!");
						return true;
					}else{
						if(db.transfer(args[1], args[2], Double.parseDouble(args[3]))){
							sender.sendMessage("$ " + args[3] + "has successfully been transfered to " + args[2]);
							System.out.println(String.valueOf(db.getBalance(args[1])) + " " + String.valueOf(db.getBalance(args[2])));
							return true;
						}else{
							sender.sendMessage(ChatColor.DARK_RED + "An error occured while transfering money. Please inform the administrator!");
							return true;
						}
					}
				}else if(!perm.user(sender, args)){
					sender.sendMessage(ChatColor.RED + "You don't have permissions to do that.");
				}else if(args.length > 1 && db.getRights(args[1], sender.getName()) == 0){
					sender.sendMessage(ChatColor.RED + "You do not have permissions for that account!");
				}else{
					sender.sendMessage("Oops! Something went wrong!");
					sender.sendMessage("Are you sure that you haven't done anything wrong? Type /account help for help.");
				}
				return true;
			}
		}catch(CommandException e){
			sender.sendMessage("Oops! Something went wrong!");
			sender.sendMessage("Are you sure that you haven't done anything wrong? Type /account help for help.");
		}
			return false;
	}
}