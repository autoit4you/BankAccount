package me.autoit4you.bankaccount.commands;

import java.text.DecimalFormat;

import me.autoit4you.bankaccount.BankAccount;
import me.autoit4you.bankaccount.exceptions.*;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandAccountBalance extends BankAccountCommand {

	@Override
	public void run(CommandSender sender, String[] args)
			throws BankAccountException {
		if(args.length < 2 || args[1] == null)
			throw new BAArgumentException("Please review your arguments!");
		
		if(!BankAccount.perm.user(sender, args))
			throw new CommandPermissionException();
		
		int access = BankAccount.db.getRights(args[1], sender.getName());
		if(access < 1) {
			throw new AccountAccessException(access);
		}
		
		DecimalFormat df = new DecimalFormat(",##0.00");
		sender.sendMessage(ChatColor.GOLD + args[1] + ": " + df.format(BankAccount.db.getBalance(args[1])));
	}
	
}
