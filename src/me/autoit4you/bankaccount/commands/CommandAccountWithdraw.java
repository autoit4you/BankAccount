package me.autoit4you.bankaccount.commands;

import me.autoit4you.bankaccount.BankAccount;
import me.autoit4you.bankaccount.exceptions.*;

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
			
		}catch(NumberFormatException e) {
			throw new BAArgumentException("That is not a valid number!");
		}
	}

}
