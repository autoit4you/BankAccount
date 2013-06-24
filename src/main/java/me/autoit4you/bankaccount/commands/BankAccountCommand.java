package me.autoit4you.bankaccount.commands;

import java.util.List;

import me.autoit4you.bankaccount.exceptions.BankAccountException;

import org.bukkit.command.CommandSender;

public abstract class BankAccountCommand {
	
	public abstract void run(CommandSender sender, String[] args)
		throws BankAccountException;
	
	public abstract List<String> tab(CommandSender sender, String[] args);
}
