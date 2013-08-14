package de.autoit4you.bankaccount;

import de.autoit4you.bankaccount.exceptions.DatabaseConnectException;
import de.autoit4you.bankaccount.exceptions.DatabaseSQLException;

import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Set;

public abstract class Database {

	public abstract Connection connectDB() throws DatabaseConnectException;
	
	public abstract void checkDB(Connection conn);

    public abstract Set<String> getAccounts() throws DatabaseConnectException, DatabaseSQLException;

    public abstract void setAccount(String account, double money, HashMap<String, Integer> map) throws DatabaseConnectException, DatabaseSQLException;

    public abstract HashMap<String, Object> getAccount(String account) throws DatabaseConnectException, DatabaseSQLException;

    protected double roundDouble(double number){
        DecimalFormat twoDP = new DecimalFormat("#.##");
        String twoDecimalPlaces = twoDP.format(number);
        return Double.valueOf(twoDecimalPlaces.replace(",", "."));
    }
}