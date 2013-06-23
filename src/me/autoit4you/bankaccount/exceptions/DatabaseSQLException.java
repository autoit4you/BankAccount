package me.autoit4you.bankaccount.exceptions;

import me.autoit4you.bankaccount.BankAccount;

import org.bukkit.command.CommandSender;

public class DatabaseSQLException extends BankAccountException {

	private static final long serialVersionUID = -1616452545234807124L;
	private String e;
	
	public DatabaseSQLException(String e) {
		this.e = e;
	}
	
	@Override
	public void print(CommandSender sender, String[] args) {
		BankAccount.log.severe(e);
	}

}
