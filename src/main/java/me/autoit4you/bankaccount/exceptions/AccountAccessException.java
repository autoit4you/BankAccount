package me.autoit4you.bankaccount.exceptions;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class AccountAccessException extends BankAccountException {

	public AccountAccessException(int access) {
		this.type = access;
	}
	
	public enum AccountAccess {
		noaccess, nowrite
	}
	
	private int type;
	private static final long serialVersionUID = -5350625842622253907L;

	@Override
	public void print(CommandSender sender, String[] args) {
		switch(this.type) {
			case 0:
				sender.sendMessage(ChatColor.RED + "You don't have access to this account!");
			case 1:
				sender.sendMessage(ChatColor.RED + "You must be account admin to do this!");
			case 2:
				sender.sendMessage(ChatColor.RED + "You must be account owner to do this!");
		}
	}

}
