package me.autoit4you.bankaccount.commands;

import java.util.List;

import me.autoit4you.bankaccount.BankAccount;
import me.autoit4you.bankaccount.exceptions.BAArgumentException;
import me.autoit4you.bankaccount.exceptions.BankAccountException;

import me.autoit4you.bankaccount.exceptions.CommandPermissionException;
import org.bukkit.command.CommandSender;

public abstract class BankAccountCommand {
	
	public abstract void run(CommandSender sender, String[] args, BankAccount plugin) throws BAArgumentException, CommandPermissionException;
	
	public abstract List<String> tab(CommandSender sender, String[] args);
}
