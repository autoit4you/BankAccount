package de.autoit4you.bankaccount.api;

import de.autoit4you.bankaccount.Database;
import de.autoit4you.bankaccount.exceptions.BankAccountException;
import de.autoit4you.bankaccount.exceptions.DatabaseConnectException;
import de.autoit4you.bankaccount.exceptions.DatabaseSQLException;

import java.sql.Connection;
import java.util.HashMap;

class DatabaseTestUnit extends Database {
    @Override
    public Connection connectDB() throws BankAccountException {
        return null;
    }

    @Override
    public void checkDB(Connection conn) {
    }

    @Override
    public void createAccount(String user, String account) throws DatabaseConnectException, DatabaseSQLException {
    }

    @Override
    public int removeAccount(String user, String account) throws DatabaseConnectException, DatabaseSQLException {
        return 0;
    }

    @Override
    public HashMap<String, String> getAccounts(String user) throws DatabaseConnectException, DatabaseSQLException {
        return null;
    }

    @Override
    public HashMap<String, String> getAllAccounts() throws DatabaseConnectException, DatabaseSQLException {
        return null;
    }

    @Override
    public void setMoney(String account, Double money) throws DatabaseConnectException, DatabaseSQLException {
    }

    @Override
    public double getMoney(String account) throws DatabaseConnectException, DatabaseSQLException {
        return 0;
    }

    @Override
    public HashMap<String, Integer> getRights(String account) throws DatabaseConnectException, DatabaseSQLException {
        return null;
    }

    @Override
    public void setRights(String account, HashMap<String, Integer> map) throws DatabaseConnectException, DatabaseSQLException {
    }

    @Override
    public Double getBalance(String account) throws DatabaseConnectException, DatabaseSQLException {
        return null;
    }
}
