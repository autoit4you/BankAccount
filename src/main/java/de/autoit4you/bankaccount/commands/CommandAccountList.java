package de.autoit4you.bankaccount.commands;

import java.util.HashMap;
import java.util.List;

import de.autoit4you.bankaccount.BankAccount;
import de.autoit4you.bankaccount.api.Account;
import de.autoit4you.bankaccount.exceptions.*;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandAccountList extends BankAccountCommand {

	@Override
	public void run(CommandSender sender, String[] args, BankAccount plugin)
            throws BAArgumentException, CommandPermissionException {
		if(!BankAccount.perm.user(sender, args))
			throw new CommandPermissionException();

        HashMap<String, Integer> access = new HashMap<String, Integer>();

		for(Account acc : plugin.getAPI().getAccounts()) {
            if(acc.getAccess(sender.getName()) != -1)
                access.put(acc.getName(), acc.getAccess(sender.getName()));
        }

        for(int i=1; i<4; i++){
            String names = "";
            for(String account : access.keySet()) {
                if(access.get(account) == i)
                    names = names + account + ", ";
            }
            if(names != "")
                sender.sendMessage(ChatColor.BLUE + plugin.getLanguageManager().getLocalString("command.list." + i) + ": " +
                        names.substring(0, names.length() - 2));
        }
	}

	@Override
	public List<String> tab(CommandSender sender, String[] args) {
		return null;
	}

}
