package de.autoit4you.bankaccount.commands;

import de.autoit4you.bankaccount.BankAccount;
import de.autoit4you.bankaccount.exceptions.BAArgumentException;
import de.autoit4you.bankaccount.exceptions.CommandPermissionException;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BankAccountAccountExecutor implements CommandExecutor {
    private BankAccount plugin;

    public BankAccountAccountExecutor(BankAccount plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("You must be a player to use BankAccount!");
            return false;
        }else if(cmd.getName().equalsIgnoreCase("account")){
            try {
                if(args.length < 1 || args[0].equalsIgnoreCase("help")) {
                    new CommandAccountHelp().run(sender, args, plugin);
                } else if(args[0].equalsIgnoreCase("open")) {
                    new CommandAccountOpen().run(sender, args, plugin);
                }else if(args[0].equalsIgnoreCase("close")){
                    new CommandAccountClose().run(sender, args, plugin);
                }else if(args[0].equalsIgnoreCase("list")){
                    new CommandAccountList().run(sender, args, plugin);
                }else if(args[0].equalsIgnoreCase("withdraw")){
                    new CommandAccountWithdraw().run(sender, args, plugin);
                }else if(args[0].equalsIgnoreCase("deposit")){
                    new CommandAccountDeposit().run(sender, args, plugin);
                }else if(args[0].equalsIgnoreCase("balance")){
                    new CommandAccountBalance().run(sender, args, plugin);
                }else if(args[0].equalsIgnoreCase("top")){
                    new CommandAccountTop().run(sender, args, plugin);
                }else if(args[0].equalsIgnoreCase("transfer")){
                    new CommandAccountTransfer().run(sender, args, plugin);
                }else if(args[0].equalsIgnoreCase("adduser")) {
                    new CommandAccountAdduser().run(sender, args, plugin);
                }else if(args[0].equalsIgnoreCase("removeuser")) {
                    new CommandAccountRemoveuser().run(sender, args, plugin);
                }else if(args[0].equalsIgnoreCase("addadmin")) {
                    new CommandAccountAddadmin().run(sender, args, plugin);
                }else if(args[0].equalsIgnoreCase("removeadmin")) {
                    new CommandAccountRemoveadmin().run(sender, args, plugin);
                }else if(args[0].equalsIgnoreCase("transferownership")) {
                    new CommandAccountTransferownership().run(sender, args, plugin);
                } else {
                    sender.sendMessage(ChatColor.RED + "That command is not recognized. Please type /account help for help.");
                }
            } catch (BAArgumentException argument) {
                sender.sendMessage(ChatColor.RED + "Please check your arguments!");
            } catch (CommandPermissionException perm) {
                sender.sendMessage(ChatColor.RED + "You do not have permissions!");
            }
            return true;
        }
        return false;
    }
}
