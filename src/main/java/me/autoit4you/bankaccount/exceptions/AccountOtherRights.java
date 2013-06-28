package me.autoit4you.bankaccount.exceptions;

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
		// TODO Auto-generated method stub
		
	}

}
