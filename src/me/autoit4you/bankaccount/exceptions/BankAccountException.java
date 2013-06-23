package me.autoit4you.bankaccount.exceptions;

import org.bukkit.command.CommandSender;

public abstract class BankAccountException extends Exception {

	private static final long serialVersionUID = -5695575094371183661L;
	
	public abstract void print(CommandSender sender, String[] args);

}
