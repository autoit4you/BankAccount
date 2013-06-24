package me.autoit4you.bankaccount.commands;

import java.util.List;

import me.autoit4you.bankaccount.BankAccount;
import me.autoit4you.bankaccount.exceptions.*;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandAccountTransfer extends BankAccountCommand {

	@Override
	public void run(CommandSender sender, String[] args)
			throws BankAccountException {
		if(args.length < 4 || args[1] == null || args[2] == null || args[3] == null)
			throw new BAArgumentException("Please review your arguments!");
		
		if(!BankAccount.perm.user(sender, args))
			throw new CommandPermissionException();
		
		int access = BankAccount.db.getRights(args[1], sender.getName());
		if(access < 2) {
			throw new AccountAccessException(access);
		}
		
		if(!BankAccount.db.existAccount(args[1])) {
			throw new AccountExistException("The sender account must exist!");
		}
		if(!BankAccount.db.existAccount(args[2])) {
			throw new AccountExistException("The receiver account must exist!");
		}
		if(args[1].equals(args[2])) {
			sender.sendMessage(ChatColor.GOLD + "Why would you send money to yourself?");
			return;
		}
		
		try{
			if(BankAccount.db.checkmoney(args[1], Double.valueOf(args[3]))) {
				BankAccount.db.transfer(args[1], args[2], Double.valueOf(args[3]));
				
				String currency = BankAccount.vault.getMoneyIcon(args[3]);
				sender.sendMessage(args[3] + " " + currency + " has successfully been transfered to " + args[2]);
			}else {
				sender.sendMessage(ChatColor.RED + "The sender account has not enough money!");
			}
		}catch(NumberFormatException e) {
			throw new BAArgumentException("That is not a valid number!");
		}
		
	}

	@Override
	public List<String> tab(CommandSender sender, String[] args) {
		return null;
	}

}
