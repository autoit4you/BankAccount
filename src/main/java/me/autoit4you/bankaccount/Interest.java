package me.autoit4you.bankaccount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import me.autoit4you.bankaccount.exceptions.BankAccountException;

import org.bukkit.Bukkit;

public class Interest implements Runnable {
	private Double percentage;
	private boolean online;
	
	public Interest(double percentage, boolean online) {
		this.percentage = percentage;
		this.online = online;
	}

	@Override
	public void run(){
		HashMap<String, String> accounts;
		try {
			accounts = BankAccount.db.getAllAccounts();
		} catch(BankAccountException banke) {
			banke.print(null, null);
			return;
		}
		List<String> intAccounts = new ArrayList<String>();
		
		for(Entry<String, String> e : accounts.entrySet()){
			if(this.online){
				if(Bukkit.getOfflinePlayer(e.getValue()).isOnline())
					intAccounts.add(e.getKey());
			}else{
				intAccounts.add(e.getKey());
			}
		}
		
		for(String s : intAccounts){
			try {
				BankAccount.db.interest(s, this.percentage);
			} catch (BankAccountException e1) {
				e1.print(null, null);
				return;
			}
		}
	}

}
