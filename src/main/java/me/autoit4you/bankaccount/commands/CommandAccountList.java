package me.autoit4you.bankaccount.commands;

import java.util.HashMap;
import java.util.Map.Entry;
import me.autoit4you.bankaccount.BankAccount;
import me.autoit4you.bankaccount.exceptions.*;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandAccountList extends BankAccountCommand {

	@Override
	public void run(CommandSender sender, String[] args)
			throws BankAccountException {
		if(!BankAccount.perm.user(sender, args))
			throw new CommandPermissionException();
		
		String user = "";
		String owner = "";
		HashMap<String, String> accounts = BankAccount.db.getAccounts(sender.getName());
		
		for(Entry<String, String> account : accounts.entrySet()) {
			if(account.getValue() == "1")
				user += account.getKey() + ", ";
			else if(account.getValue() == "2")
				owner += account.getKey() + ", ";
		}
		
		if(user.indexOf(",") != -1){
			user = user.substring(0, user.length() - 2);
		}
		if(owner.indexOf(",") != -1){
			owner = owner.substring(0, owner.length() - 2);
		}
		
		if(owner == "" && user == "") {
			sender.sendMessage(ChatColor.RED + "You don't have any accounts");
		}else {
			if(owner != "")
				sender.sendMessage("Owner: " + owner);
			if(user != "")
				sender.sendMessage("User: " + user);
		}
	}

}
