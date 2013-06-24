package me.autoit4you.bankaccount.commands;

import java.util.List;

import me.autoit4you.bankaccount.BankAccount;
import me.autoit4you.bankaccount.exceptions.*;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandAccountDeposit extends BankAccountCommand {

	@Override
	public void run(CommandSender sender, String[] args)
			throws BankAccountException {
		if(args.length < 3 || args[1] == null || args[2] == null)
			throw new BAArgumentException("Please review your arguments!");
		
		if(!BankAccount.perm.user(sender, args))
			throw new CommandPermissionException();
		
		int access = BankAccount.db.getRights(args[1], sender.getName());
		if(access != 2) {
			throw new AccountAccessException(access);
		}
		
		try{
			if(BankAccount.vault.hasMoney(sender.getName(), Double.valueOf(args[2]))) {
				BankAccount.db.depositMoney(args[1], Double.valueOf(args[2]));
				BankAccount.vault.depositMoney(sender.getName(), Double.parseDouble(args[2]));
				sender.sendMessage(ChatColor.GOLD + "You sent $" + args[2] + " to the account '" + args[1] + "'");
			}else {
				sender.sendMessage(ChatColor.GOLD + "You don't have enough money!");
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
