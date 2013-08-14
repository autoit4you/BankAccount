package de.autoit4you.bankaccount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import de.autoit4you.bankaccount.api.Account;
import de.autoit4you.bankaccount.exceptions.BankAccountException;

import org.bukkit.Bukkit;

public class Interest implements Runnable {
	private Double percentage;
	private boolean online;
    private BankAccount plugin;
	
	public Interest(double percentage, boolean online) {
		this.percentage = percentage;
		this.online = online;
	}

	@Override
	public void run(){
		List<Account> accounts;

		accounts = plugin.getAPI().getAccounts();

		List<Account> intAccounts = new ArrayList<Account>();
		
		for(Account account : accounts){
			if(this.online){
				if(Bukkit.getOfflinePlayer(account.getOwner()).isOnline())
					intAccounts.add(account);
			}else{
				intAccounts.add(account);
			}
		}
		
		for(Account s : intAccounts){

			s.interest(percentage);

		}
	}

}
