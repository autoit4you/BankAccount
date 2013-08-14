package de.autoit4you.bankaccount;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import de.autoit4you.bankaccount.exceptions.*;

public class SQLite extends Database {
	private final String file;

	public SQLite(String file) throws Exception {
		super();
		this.file = file;
		Class.forName("org.sqlite.JDBC");
		Connection conn = connectDB();
		if(conn == null)
			throw new Exception("SQLite database not accessible");
		checkDB(conn);
	}

    @Override
    public Connection connectDB() throws DatabaseConnectException {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + file);

            if(conn == null)
                throw new DatabaseConnectException();
            return conn;
        } catch (Exception ex) {
            throw new DatabaseConnectException();
        }
    }
	
	@Override
	public void checkDB(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `access` (" +
					"'account' varchar(255) NOT NULL," +
					"'name' varchar(255) NOT NULL," +
					"'accesstype' int(11) NOT NULL);");
			stmt.executeUpdate("CREATE UNIQUE INDEX `uni1` ON `access` ('account', 'name');");
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `accounts` (" +
					"'name' varchar(255) NOT NULL," +
					"'money' double NOT NULL);");
			stmt.executeUpdate("CREATE UNIQUE INDEX `name` ON `accounts` ('name');");
			
		} catch (SQLException e) {
			if(BankAccount.debug)
				e.printStackTrace();
			
			return;
		}
	}

    @Override
    public Set<String> getAccounts() throws DatabaseConnectException, DatabaseSQLException {
        Set<String> accounts = new HashSet<String>();

        Connection conn = connectDB();
        if(conn == null)
            throw new DatabaseConnectException();

        try {
            Statement stmt = conn.createStatement();

            ResultSet accountSet = stmt.executeQuery("SELECT * FROM `accounts`;");
            while (accountSet.next()) {
                accounts.add(accountSet.getString("name"));
            }

            return accounts;
        } catch (SQLException e) {
            throw new DatabaseSQLException(e.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }

    @Override
    public void setAccount(String account, double money, HashMap<String, Integer> map) throws DatabaseConnectException, DatabaseSQLException {
        Connection conn = connectDB();
        if(conn == null)
            throw new DatabaseConnectException();

        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM `accounts` WHERE `name` = '" + account + "';");
            stmt.executeUpdate("INSERT INTO `accounts` (`name`, `money`)  VALUES (" + account + "', '" + roundDouble(money) + "');");

            stmt.executeUpdate("DELETE FROM `access` WHERE `account` = '" + account + "';");

            for(String name  : map.keySet()) {
                stmt.executeUpdate("INSERT INTO `access` (`account`, `name`, `accesstype`) VALUES ('" + account + "', '" + name + "', '" + map.get(name) + "');");
            }
        } catch (SQLException e) {
            throw new DatabaseSQLException(e.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }

    @Override
    public HashMap<String, Object> getAccount(String account) throws DatabaseConnectException, DatabaseSQLException {
        //Results
        String name = "";
        double money = 0.0000000123456789;
        HashMap<String, Integer> users = new HashMap<String, Integer>();
        //Logic
        Connection conn = connectDB();
        if(conn == null)
            throw new DatabaseConnectException();

        try {
            Statement stmt = conn.createStatement();

            ResultSet accountSet = stmt.executeQuery("SELECT * FROM `accounts` WHERE `name` = '" + account + "';");
            while (accountSet.next()) {
                name = accountSet.getString("name");
                money = accountSet.getDouble("money");
            }

            ResultSet accessSet = stmt.executeQuery("SELECT * FROM `access` WHERE `account` = '" + account + "';");
            while (accessSet.next()) {
                users.put(accessSet.getString("name"), accessSet.getInt("accesstype"));
            }

            if (name == "" || money == 0.0000000123456789)
                throw new SQLException("Generic SQL exception");

            HashMap<String, Object> result = new HashMap<String, Object>();
            result.put("name", name);
            result.put("money", money);
            result.put("users", users);
            return result;
        } catch (SQLException e) {
            throw new DatabaseSQLException(e.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }
}
