package me.autoit4you.bankaccount;

import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.HashMap;

import me.autoit4you.bankaccount.exceptions.BankAccountException;

public abstract class Database {

	public abstract Connection connectDB() throws BankAccountException;
	
	public abstract void checkDB(Connection conn);

	public abstract boolean checkmoney(String account, Double money) throws BankAccountException;
	
	public abstract boolean existAccount(String account) throws BankAccountException;
	
	public abstract void createAccount(String user, String account) throws BankAccountException;
	
	public abstract int removeAccount(String user, String account) throws BankAccountException;
	
	public abstract HashMap<String, String> getAccounts(String user) throws BankAccountException;
	
	public abstract HashMap<String, String> getAllAccounts() throws BankAccountException;
	
	public abstract void depositMoney(String account, Double money) throws BankAccountException;
	
	public abstract void withdrawMoney(String account, Double money) throws BankAccountException;
	
	public abstract int getRights(String account, String player) throws BankAccountException;
	
	public abstract Double getBalance(String account) throws BankAccountException;
	
	public abstract void transfer(String account1, String account2, Double money) throws BankAccountException;
	
	public abstract void interest(String account, Double percent) throws BankAccountException;
	
	public abstract void addUser(String account, String player, int accesslevel) throws BankAccountException;
	
	public abstract void removeUser(String account, String player) throws BankAccountException;
	
	double roundDouble(double number){
		DecimalFormat twoDP = new DecimalFormat("#.##");
		String twoDecimalPlaces = twoDP.format(number);
		return Double.valueOf(twoDecimalPlaces.replace(",", "."));
	}
}