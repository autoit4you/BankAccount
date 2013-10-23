package de.autoit4you.bankaccount.commands.account;

import java.util.List;

import de.autoit4you.bankaccount.BankAccount;
import de.autoit4you.bankaccount.Permissions;
import de.autoit4you.bankaccount.api.Account;
import de.autoit4you.bankaccount.api.TransactionType;
import de.autoit4you.bankaccount.commands.BankAccountCommand;
import de.autoit4you.bankaccount.exceptions.*;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandAccountDeposit extends BankAccountCommand {

	@Override
	public void run(CommandSender sender, String[] args, BankAccount plugin)
			throws BAArgumentException, CommandPermissionException {
		if(args.length < 3 || args[1] == null || args[2] == null)
			throw new BAArgumentException();
		
		if(!plugin.getPermissions().user(sender, Permissions.Permission.ACCOUNT_DEPOSIT))
			throw new CommandPermissionException();
		
		try{
            Account acc = plugin.getAPI().getAccountByName(args[1]);
            if(acc.getAccess(sender.getName()) < 2) {
                sender.sendMessage(ChatColor.RED + "You do not have access to this account!");
                return;
            }

			if(plugin.getVault().hasMoney(sender.getName(), Double.parseDouble(args[2]))) {
                acc.setMoney(acc.getMoney() + Double.parseDouble(args[2]), TransactionType.PLUGIN);
				plugin.getVault().depositMoney(sender.getName(), Double.parseDouble(args[2]));
				sender.sendMessage(ChatColor.GOLD + "You sent $" + args[2] + " to the account '" + args[1] + "'");
			}else {
				sender.sendMessage(ChatColor.GOLD + "You don't have enough money!");
			}
		} catch(NumberFormatException e) {
			sender.sendMessage(ChatColor.RED + "That is not a valid number!");
		} catch (AccountExistException a) {
            sender.sendMessage(ChatColor.RED + "That account does not exist!");
        }
	}

	@Override
	public List<String> tab(CommandSender sender, String[] args) {
		return null;
	}

}
