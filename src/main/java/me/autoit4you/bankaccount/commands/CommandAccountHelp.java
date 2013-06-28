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
		
		int page = 1;
		sender.sendMessage(ChatColor.GOLD + "BankAccount Help - Page " + page + "/0");
		
		if(page == 1) {
			sender.sendMessage("/account help <PAGE> - Shows this help");
			sender.sendMessage("/account open <NAME> - Opens a new account");
			sender.sendMessage("/account close <NAME> - Closes a account");
			sender.sendMessage("/account list - Lists all accounts you have access to");
			sender.sendMessage("/account balance <NAME> - Shows the balance of the specified account");
		}else if(page == 2) {
			sender.sendMessage("/account withdraw <NAME> <AMOUNT> - Withdraws money from a account you have access to");
			sender.sendMessage("/account deposit <NAME> <AMOUNT> - Deposits money to a account you have access to");
			sender.sendMessage("/account transfer <FROM> <TO> <AMOUNT> - Transfers the specified value from account FROM to account TO");
		}else if(page == 3) {
			sender.sendMessage("/account adduser <ACCOUNT> <NAME> - Gives NAME useraccess to ACCOUNT");
			sender.sendMessage("/account removeuser <ACCOUNT> <NAME> - Removes NAME's useraccess to ACCOUNT");
			sender.sendMessage("/account addadmin <ACCOUNT> <NAME> - Gives NAME adminaccess to ACCOUNT");
			sender.sendMessage("/account removeadmin <ACCOUNT> <NAME> - Removes NAME's adminaccess to ACCOUNT");
		}else if(page == 4) {
			sender.sendMessage("/account transferownership <ACCOUNT> <NAME> - Transfers the ownership of ACCOUNT to NAME");
			sender.sendMessage(ChatColor.BOLD + "Notice: " + ChatColor.DARK_RED + "You will not be able to use the account anymore because you give your ownership away!");
			sender.sendMessage(ChatColor.DARK_RED + "If you want to still access that account the new owner must give you access to it!");
		}else {
			
		}
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
