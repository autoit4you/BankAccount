package me.autoit4you.bankaccount.commands;

import java.util.List;

import me.autoit4you.bankaccount.BankAccount;
import me.autoit4you.bankaccount.exceptions.*;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandAccountAdduser extends BankAccountCommand {

	@Override
	public void run(CommandSender sender, String[] args)
			throws BankAccountException {
		if(args.length < 3 || args[1] == null)
			throw new BAArgumentException("Please review your arguments!");
		
		if(!BankAccount.perm.user(sender, args))
			throw new CommandPermissionException();
		
		if(!BankAccount.db.existAccount(args[1]))
			throw new AccountExistException();
		
		int access = BankAccount.db.getRights(args[1], sender.getName());
		if(access < 2) {
			throw new AccountAccessException(access);
		}
		
		BankAccount.db.addUser(args[1], args[2], 1);
		sender.sendMessage(ChatColor.GOLD + args[2] + ChatColor.GREEN + " has now access to the account " + args[1] + "");
	}

	@Override
	public List<String> tab(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}

}
