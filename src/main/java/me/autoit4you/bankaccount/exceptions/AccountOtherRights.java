package me.autoit4you.bankaccount.exceptions;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class AccountOtherRights extends BankAccountException {

	private static final long serialVersionUID = 4496916336690626497L;
	private int access;
	private String user;
	
	public AccountOtherRights(int access, String user) {
		this.access = access;
		this.user = user;
	}

	@Override
	public void print(CommandSender sender, String[] args) {
		sender.sendMessage(ChatColor.RED + "The user " + this.user + "  already has permissions for that account!");
		
		if(this.access == 1) {
			sender.sendMessage(ChatColor.RED + "He is currently account user.");
		}else if(this.access == 2) {
			sender.sendMessage(ChatColor.RED + "He is currently account admin.");
		}else if(this.access == 3) {
			sender.sendMessage(ChatColor.RED + "He is currently account owner.");
		}
	}

}
