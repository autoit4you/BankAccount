package de.autoit4you.bankaccount.tasks;

import java.util.*;

import de.autoit4you.bankaccount.BankAccount;
import de.autoit4you.bankaccount.api.Account;

import org.bukkit.Bukkit;
import org.bukkit.permissions.PermissibleBase;

public class Interest implements Runnable {
	private boolean online;
    private BankAccount plugin;
    private double percentage;
    private String name;
	
	public Interest(BankAccount plugin, boolean online, double percentage, String name) {
		this.plugin = plugin;
        this.online = online;
        this.percentage = percentage;
	}

	@Override
	public void run(){
		Set<Account> accounts;

		accounts = plugin.getAPI().getAccounts();
        Set<Account> intAccounts = new HashSet<Account>();


        for(Account account : accounts){
            if(new PermissibleBase(Bukkit.getOfflinePlayer(account.getOwner())).hasPermission("bankaccount.interest.default")) {
                continue;
            } else if(new PermissibleBase(Bukkit.getOfflinePlayer(account.getOwner())).hasPermission("bankaccount.interest." + name)) {
                if(this.online){
                    if(Bukkit.getOfflinePlayer(account.getOwner()).isOnline())
                        intAccounts.add(account);
                }else{
                    intAccounts.add(account);
                }
            }
		}

        for (Account account : intAccounts) {
            account.interest(percentage);
        }
	}

}
