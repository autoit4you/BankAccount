package me.autoit4you.bankaccount.exceptions;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandCustomException extends BankAccountException {

	private static final long serialVersionUID = 5730605181631447202L;
	
	String emsg = "";
	
	public CommandCustomException(String exceptionmsg) {
		this.emsg = exceptionmsg;
	}
	
	@Override
	public void print(CommandSender sender, String[] args) {
		sender.sendMessage(ChatColor.RED + this.emsg);
	}
}