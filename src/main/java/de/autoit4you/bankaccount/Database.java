package de.autoit4you.bankaccount;

import java.sql.Connection;
import java.util.HashMap;

import de.autoit4you.bankaccount.exceptions.*;

public abstract class Database {

	public abstract Connection connectDB() throws BankAccountException;
	
	public abstract void checkDB(Connection conn);

	public abstract void createAccount(String user, String account) throws DatabaseConnectException, DatabaseSQLException;
	
	public abstract int removeAccount(String user, String account) throws DatabaseConnectException, DatabaseSQLException;
	
	public abstract HashMap<String, String> getAccounts(String user) throws DatabaseConnectException, DatabaseSQLException;
	
	public abstract HashMap<String, String> getAllAccounts() throws DatabaseConnectException, DatabaseSQLException;
	
	public abstract void setMoney(String account, Double money) throws DatabaseConnectException, DatabaseSQLException;
	
	public abstract double getMoney(String account) throws DatabaseConnectException, DatabaseSQLException;
	
	public abstract HashMap<String, Integer> getRights(String account) throws DatabaseConnectException, DatabaseSQLException;

    public abstract void setRights(String account, HashMap<String, Integer> map) throws DatabaseConnectException, DatabaseSQLException;
	
	public abstract Double getBalance(String account) throws DatabaseConnectException, DatabaseSQLException;
}