package de.autoit4you.bankaccount.commands;

import java.util.List;

import de.autoit4you.bankaccount.BankAccount;
import de.autoit4you.bankaccount.api.Account;
import de.autoit4you.bankaccount.api.TransactionType;
import de.autoit4you.bankaccount.exceptions.*;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandAccountTransfer extends BankAccountCommand {

	@Override
	public void run(CommandSender sender, String[] args, BankAccount plugin)
			throws BAArgumentException, CommandPermissionException {
		if(args.length < 4 || args[1] == null || args[2] == null || args[3] == null)
			throw new BAArgumentException();
		
		if(!BankAccount.perm.user(sender, args))
			throw new CommandPermissionException();

		if(args[1].equals(args[2])) {
			sender.sendMessage(ChatColor.RED + "Why would you send money to the same account as the sender?");
			return;
		}
		
		try{
            double amount = Double.parseDouble(args[3]);

            if(plugin.getAPI().getAccountByName(args[1]).getMoney() < amount) {
                sender.sendMessage(ChatColor.GOLD + "You don't have enough money!");
                return;
            }

            if(plugin.getAPI().getAccountByName(args[1]).getAccess(sender.getName()) > 1) {
                Account send = plugin.getAPI().getAccountByName(args[1]);
                send.setMoney(send.getMoney() - amount, TransactionType.PLUGIN);
            } else {

            }
		} catch(NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "That is not a valid number!");
		} catch (AccountExistException exist) {
            sender.sendMessage(ChatColor.RED + "That account does not exist!");
        }
		
	}

	@Override
	public List<String> tab(CommandSender sender, String[] args) {
		return null;
	}

}
