package me.autoit4you.bankaccount.exceptions;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class AccountExistException extends BankAccountException {

	private static final long serialVersionUID = -8623493378954328995L;
	private String msg;
	
	public AccountExistException() {
		this.msg = "That account does not exist!";
	}
	
	public AccountExistException(String msg) {
		this.msg = msg;
	}
	
	@Override
	public void print(CommandSender sender, String[] args) {
		sender.sendMessage(ChatColor.RED + this.msg);
	}

}
