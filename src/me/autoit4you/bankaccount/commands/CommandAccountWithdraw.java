package me.autoit4you.bankaccount.commands;

import me.autoit4you.bankaccount.BankAccount;
import me.autoit4you.bankaccount.exceptions.*;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandAccountWithdraw extends BankAccountCommand {

	@Override
	public void run(CommandSender sender, String[] args)
			throws BankAccountException {
		if(args.length < 2 || args[1] == null || args[2] == null)
			throw new BAArgumentException("Please review your arguments!");
		
		if(!BankAccount.perm.user(sender, args))
			throw new CommandPermissionException();
		
		int access = BankAccount.db.getRights(args[1], sender.getName());
		if(access != 2) {
			throw new AccountAccessException(access);
		}
		
		try{
			if(BankAccount.db.checkmoney(args[1], Double.valueOf(args[2]))) {
				BankAccount.db.withdrawMoney(args[1], Double.valueOf(args[2]));
				BankAccount.vault.withdrawMoney(sender.getName(), Double.parseDouble(args[2]));
				sender.sendMessage(ChatColor.GOLD + "You withdraw $" + args[2] + " from the account '" + args[1] + "'");
			}else {
				sender.sendMessage(ChatColor.RED + "You don't have enough money on that account!");
			}
		}catch(NumberFormatException e) {
			throw new BAArgumentException("That is not a valid number!");
		}
	}

}
