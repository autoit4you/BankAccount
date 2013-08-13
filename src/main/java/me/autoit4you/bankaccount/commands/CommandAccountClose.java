package me.autoit4you.bankaccount.commands;

import java.util.List;

import me.autoit4you.bankaccount.BankAccount;
import me.autoit4you.bankaccount.api.Account;
import me.autoit4you.bankaccount.exceptions.*;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandAccountClose extends BankAccountCommand {

	@Override
	public void run(CommandSender sender, String[] args, BankAccount plugin)
			throws BAArgumentException, CommandPermissionException {
		if(args.length < 1 || args[1] != null)
			throw new BAArgumentException();
		
		if(!BankAccount.perm.user(sender, args))
			throw new CommandPermissionException();

        try {
            Account acc = plugin.getAPI().getAccountByName(args[1].toString());
            if(acc.getAccess(sender.getName()) == 3) {
                plugin.getAPI().close(args[1].toString());
            } else if(acc.getAccess(sender.getName()) == -1) {
                sender.sendMessage(ChatColor.RED + "You do not have any permissions for this account!");
            } else {
                acc.removeAccess(sender.getName());
                sender.sendMessage(ChatColor.GREEN + "Removed your access for this account!");
            }
        } catch (AccountExistException e) {
            sender.sendMessage(ChatColor.RED + "Account does not exist!");
        }
	}

	@Override
	public List<String> tab(CommandSender sender, String[] args) {
		return null;
	}

}
