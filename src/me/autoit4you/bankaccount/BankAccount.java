package me.autoit4you.bankaccount;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.logging.Logger;

import me.autoit4you.bankaccount.commands.*;
import me.autoit4you.bankaccount.exceptions.BankAccountException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.mcstats.MetricsLite;

	public class BankAccount extends JavaPlugin{
		//variables
		public static boolean debug = false;
	    public static final Logger log = Logger.getLogger("Minecraft");
	    public static Database db = null;
	    public static Vault vault = null;
	    public static Permissions perm = null;
		private BukkitTask sinterest;

	    @Override
	    public void onEnable() {
	    	//loading or creating config.yml
	    	if(!new File("plugins/BankAccount/config.yml").exists())
	    		saveDefaultConfig();
	    	//setting the debug value
	    	debug = getConfig().getBoolean("debug", false);
	    	//connecting to database
	    	try{
	    		if(getConfig().getString("database.type").equalsIgnoreCase("mysql")){
	    			String dbserver = getConfig().getString("database.server");
	    	    	String dbdatabase = getConfig().getString("database.database");
	    	    	String dbuser = getConfig().getString("database.username");
	    	    	String dbpwd = getConfig().getString("database.password");
	    			db = new MySQL(dbserver, dbdatabase, dbuser, dbpwd);
	    		}else{
	    			String file = getConfig().getString("database.sqlitefile");
	    			db = new SQLite("plugins/BankAccount/" + file);
	    		}
	    	} catch (Exception e) {
	    		log.severe(e.getMessage());
	    		log.severe(String.format("Cannot access database. Are you sure that it is available? Disabling..."));
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
	    	//Setting up listeners
	    	if(getConfig().getBoolean("interest.enabled")){
	    		this.sinterest = getServer().getScheduler().runTaskTimer(this, new Interest(getConfig().getDouble("interest.percentage"), getConfig().getBoolean("interest.ownerMustOnline")), 20, 20 * 60 * getConfig().getInt("interest.minutes"));
	    	}
	    }
	    
	    @Override
	    public void onDisable() {
	    	if(this.sinterest != null)
	    		this.sinterest.cancel();
	    }
	    
	    @Override
	    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) throws CommandException{
	    	try{
	    		if(!(sender instanceof Player)){
	    			sender.sendMessage("You must be a player to use BankAccount!");
	    			return false;
	    		}else if(cmd.getName().equalsIgnoreCase("account")){
	    			if(args.length < 1 || args[0].equalsIgnoreCase("help")) {
	    				new CommandAccountHelp().run(sender, args);
	    			} else if(args[0].equalsIgnoreCase("open")) {
	    				new CommandAccountOpen().run(sender, args);
	    			}else if(args[0].equalsIgnoreCase("close")){
	    				new CommandAccountClose().run(sender, args);
	    			}else if(args[0].equalsIgnoreCase("list")){
	    				new CommandAccountList().run(sender, args);
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
	    				}else if(!db.existAccount(args[2])){
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
	    						String currency = vault.getMoneyIcon(args[3]);
	    						sender.sendMessage(args[3] + " " + currency + " has successfully been transfered to " + args[2]);
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
	    	} catch(CommandException ce) {
	    		sender.sendMessage("Oops! Something went wrong!");
	    		sender.sendMessage("Are you sure that you haven't done anything wrong? Type /account help for help.");
	    		return true;
	    	} catch(BankAccountException banke) {
	    		banke.print(sender, args);
	    		return true;
	    	}
				return false;
	    }
	}