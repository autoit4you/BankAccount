package de.autoit4you.bankaccount;

import java.util.*;
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
		Set<Account> accounts;

		accounts = plugin.getAPI().getAccounts();

		Set<Account> intAccounts = new HashSet<Account>();
		
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
