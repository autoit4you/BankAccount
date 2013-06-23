package me.autoit4you.bankaccount.commands;

import me.autoit4you.bankaccount.BankAccount;
import me.autoit4you.bankaccount.exceptions.*;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandAccountClose extends BankAccountCommand {

	@Override
	public void run(CommandSender sender, String[] args)
			throws BankAccountException {
		if(args.length < 1 || args[1] != null)
			throw new BAArgumentException("Please review your arguments!");
		
		if(!BankAccount.perm.user(sender, args))
			throw new CommandPermissionException();
		
		if(!BankAccount.db.existAccount(args[1].toString())){
			sender.sendMessage(ChatColor.RED + "Account does not exist!");
			return;
		}
		
		int i = BankAccount.db.removeAccount(sender.getName(), args[1].toString());
		
		if(i == 999) {
			sender.sendMessage("Could not close the account");
			return;
		}
		if(i == 1)
			sender.sendMessage(ChatColor.GREEN + "Removed your access to the account");
		else if(i == 2)
			sender.sendMessage(ChatColor.GREEN + "Account closed!");
	}

}
