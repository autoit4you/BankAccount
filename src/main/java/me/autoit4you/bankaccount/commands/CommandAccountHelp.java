package me.autoit4you.bankaccount.commands;

import java.util.List;

import me.autoit4you.bankaccount.BankAccount;
import me.autoit4you.bankaccount.exceptions.*;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandAccountHelp extends BankAccountCommand {
	
	@Override
	public void run(CommandSender sender, String[] args) 
			throws BankAccountException {
		if(!BankAccount.perm.user(sender, args))
			throw new CommandPermissionException();
		
		sender.sendMessage(ChatColor.GOLD + "BankAccount Help Page:");
		sender.sendMessage(ChatColor.YELLOW + "/account help - Shows this help");
		sender.sendMessage(ChatColor.YELLOW + "/account open <name> - Opens a new account");
		sender.sendMessage(ChatColor.YELLOW + "/account close <name> - Closes a account");
		sender.sendMessage(ChatColor.YELLOW + "/account list - Lists all accounts you have access to");
		sender.sendMessage(ChatColor.YELLOW + "/account withdraw <name> <amount> - Withdraw money from a account you have access to");
		sender.sendMessage(ChatColor.YELLOW + "/account deposit <name> <amount> - Deposit money to a account you have access to");
		sender.sendMessage(ChatColor.YELLOW + "/account balance <name> - Shows the balance of the specified account");
		sender.sendMessage(ChatColor.YELLOW + "/account transfer <from> <to> <amount> - Transfer the specified value from account A to account B");
	}

	@Override
	public List<String> tab(CommandSender sender, String[] args) {
		return null;
	}

}
