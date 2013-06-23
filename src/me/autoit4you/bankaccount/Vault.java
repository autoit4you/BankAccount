package me.autoit4you.bankaccount;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.economy.Economy;


public class Vault {
	
	
	private Economy economy;

	public boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServicesManager().getRegistration(Economy.class);
		if(economyProvider != null)
			economy = economyProvider.getProvider();
			return (economy != null);
	}
	
	public boolean hasMoney(String player, Double amount) {
		if(economy == null)
			return false;
		else
			return economy.getBalance(player) >= amount;
	}
	
	public void depositMoney(String player, Double money) {
		economy.bankWithdraw(player, money);
	}
	
	public void withdrawMoney(String player, Double money) {
		economy.bankDeposit(player, money);
	}
	
	public Double getMoney(String player) {
		return economy.getBalance(player);
	}
	
	public String getMoneyIcon(String amount) {
		if(Double.parseDouble(amount) == 1.00d)
			return economy.currencyNameSingular();
		else
			return economy.currencyNamePlural();
	}
}
