package me.autoit4you.bankaccount.commands;

import java.text.DecimalFormat;
import java.util.List;

import me.autoit4you.bankaccount.BankAccount;
import me.autoit4you.bankaccount.exceptions.*;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandAccountBalance extends BankAccountCommand {

	@Override
	public void run(CommandSender sender, String[] args, BankAccount plugin)
			throws BAArgumentException, CommandPermissionException {
		if(args.length < 2 || args[1] == null)
			throw new BAArgumentException();
		
		if(!BankAccount.perm.user(sender, args))
			throw new CommandPermissionException();
		
        try {
            if(plugin.getAPI().getAccountByName(args[1].toString()).getAccess(sender.getName()) < 1) {
                sender.sendMessage(ChatColor.RED + "You do not have access to this account!");
            } else {
                DecimalFormat df = new DecimalFormat(",##0.00");
                sender.sendMessage(ChatColor.GOLD + args[1] + ": " + df.format(plugin.getAPI().getAccountByName(args[1].toString()).getMoney()));
            }
        } catch (AccountExistException e) {
            sender.sendMessage(ChatColor.RED + "That account does not exist!");
        }
	}

	@Override
	public List<String> tab(CommandSender sender, String[] args) {
		return null;
	}
	
}
