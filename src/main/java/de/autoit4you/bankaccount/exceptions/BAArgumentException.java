package de.autoit4you.bankaccount.exceptions;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class BAArgumentException extends BankAccountException {

	private static final long serialVersionUID = -6268450886950214828L;
	private String msg = "";
	
	public BAArgumentException() {
        lang = "general.args";
	}
	

	public void print(CommandSender sender, String[] args) {
		sender.sendMessage(ChatColor.RED + this.msg);
	}

}
