package de.autoit4you.bankaccount.exceptions;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandCustomException extends BankAccountException {

	private static final long serialVersionUID = 5730605181631447202L;
	
	String emsg = "";
	
	public CommandCustomException(String exceptionmsg) throws UnsupportedOperationException{
		this.emsg = exceptionmsg;
        throw new UnsupportedOperationException("This exception type is not supported anymore");
	}
	

	public void print(CommandSender sender, String[] args) {
		sender.sendMessage(ChatColor.RED + this.emsg);
	}
}