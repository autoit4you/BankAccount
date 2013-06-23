package me.autoit4you.bankaccount;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import me.autoit4you.bankaccount.exceptions.*;

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
	public Connection connectDB() throws BankAccountException {
		try {
			return DriverManager.getConnection("jdbc:sqlite:" + file);
		} catch (SQLException e) {
			if(BankAccount.debug)
				e.printStackTrace();
			
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
	public boolean checkmoney(String account, Double money) throws BankAccountException {
		Connection conn = connectDB();
		if (conn == null)
			throw new DatabaseConnectException();
		try {
			Statement stmt = conn.createStatement();
			ResultSet result = stmt
					.executeQuery("SELECT * FROM `accounts` WHERE `name` = '"
							+ account + "' LIMIT 1");
			if (result.next()){
				return money <= result.getDouble("money");
			} else{
				return false;
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
	public boolean existAccount(String account) throws BankAccountException {
		Connection conn = connectDB();
		if (conn == null)
			throw new DatabaseConnectException();
		try {
			Statement stmt = conn.createStatement();
			final ResultSet result = stmt.executeQuery("SELECT * FROM `accounts` WHERE `name` = '" + account + "';");
			return result.next();
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
	public void createAccount(String user, String account) throws BankAccountException {
		Connection conn = connectDB();
		if (conn == null)
			throw new DatabaseConnectException();
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("INSERT INTO `accounts` (`name`, `money`)  " +
					"VALUES ('" + account +"', '0.00');");
			stmt.executeUpdate("INSERT INTO `access` (`account`, `name`, `accesstype`) " +
					"VALUES ('" + account +"', '" + user +"', '2');");	
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
	public int removeAccount(String user, String account) throws BankAccountException {
		Connection conn = connectDB();
		if (conn == null)
			throw new DatabaseConnectException();
		try {
			Statement stmt = conn.createStatement();
			final ResultSet result = stmt.executeQuery("SELECT `accesstype` FROM `access` WHERE `account` = '" + account + "' AND `name` = '" + user + "';");
			if(!result.next()){ return 999; }
			if(result.getInt("accesstype") == 2){
				stmt.executeUpdate("DELETE FROM `access` WHERE `account` = '" + account + "' AND `name` = '" + user +"' AND `accesstype` = 2");
				stmt.executeUpdate("DELETE FROM `accounts` WHERE `name` = '" + account + "'");
				return 2;
			}else if(result.getInt("accesstype") == 1){
				stmt.executeUpdate("DELETE FROM `access` WHERE `account` = '" + account + "' AND `name` = '" + user +"' AND `accesstype` = 1");
				return 1;
			}else{
				return 999;
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
	public HashMap<String, String> getAccounts(String user) throws BankAccountException {
		HashMap<String, String> returnResult = new HashMap<String, String>();
		Connection conn = connectDB();
		if(conn == null)
			throw new DatabaseConnectException();
			//return "An error occured while getting the bankaccounts!";
		try{
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery("SELECT `account` FROM `access` WHERE `name` = '" + user + "' AND `accesstype` = '2'");
			while(result.next()){
				if(result.getString("account") != "null"){
					returnResult.put(result.getString("account"), "2");
				}
			}
			result = stmt.executeQuery("SELECT `account` FROM `access` WHERE `name` = '" + user + "' AND `accesstype` = '1'");
			while(result.next()){
				if(result.getString("account") != "null"){
					returnResult.put(result.getString("account"), "1");
				}
			}
			return returnResult;
		} catch (SQLException e){
			throw new DatabaseSQLException(e.getMessage());
		} finally {
			try{
				conn.close();
			} catch (SQLException e){
				
			}
		}
	}
	
	public HashMap<String, String> getAllAccounts() throws BankAccountException {
		Connection conn = connectDB();
		if(conn == null)
			throw new DatabaseConnectException();
		try{
			Statement stmt = conn.createStatement();
			HashMap<String, String> accounts = new HashMap<String, String>(); 
			final ResultSet set1 = stmt.executeQuery("SELECT `name` FROM `accounts`");
			while(set1.next()){
				accounts.put(set1.getString("name"), null);
			}
			
			for(String name : accounts.keySet()){
				ResultSet set2 = stmt.executeQuery("SELECT `name` FROM `access` WHERE `account` = '" + name + "' AND `accesstype` = 2");
				while(set2.next()){
					accounts.remove(name);
					accounts.put(name, set2.getString("name"));
				}
			}
			
			return accounts;
		}catch (SQLException e){
			throw new DatabaseSQLException(e.getMessage());
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {

			}
		}
	}

	@Override
	public void depositMoney(String account, Double money) throws BankAccountException {
		Connection conn = connectDB();
		if (conn == null)
			throw new DatabaseConnectException();
		try {
			Double umoney = 0.00;
			Statement stmt = conn.createStatement();
			final ResultSet umoney2 = stmt.executeQuery("SELECT * FROM `accounts` WHERE `name` = '" + account + "';");
			while(umoney2.next()){
				if(umoney2.getDouble("money") != 0){
					umoney = umoney2.getDouble("money");
				}
			}
			stmt.executeUpdate("UPDATE `accounts` SET  `money`='" + ( roundDouble(money + umoney)) + "' WHERE `accounts`.`name` = '" + account + "'");
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
	public void withdrawMoney(String account, Double money) throws BankAccountException {
		Connection conn = connectDB();
		if (conn == null)
			throw new DatabaseConnectException();
		try {
			Double umoney = 0.00;
			Statement stmt = conn.createStatement();
			final ResultSet umoney2 = stmt.executeQuery("SELECT * FROM `accounts` WHERE `name` = '" + account + "';");
			while(umoney2.next()){
					umoney = umoney2.getDouble("money");
			}
			stmt.executeUpdate("UPDATE `accounts` SET  `money`='" + (roundDouble(umoney - money)) + "' WHERE `accounts`.`name` = '" + account + "'");
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
	public int getRights(String account, String player) throws BankAccountException {
		if(account.isEmpty())
			return 0;
		Connection conn = connectDB();
		if (conn == null)
			throw new DatabaseConnectException();
		try {
			Statement stmt = conn.createStatement();
			final ResultSet result = stmt.executeQuery("SELECT * FROM `access` WHERE `account` = '" + account + "' AND `name` = '" + player + "';");
			while(result.next()){
					return result.getInt("accesstype");
			}
			return 0;
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
	public Double getBalance(String account) throws BankAccountException {
		Connection conn = connectDB();
		if (conn == null)
			throw new DatabaseConnectException();
		try {
			Statement stmt = conn.createStatement();
			final ResultSet result = stmt.executeQuery("SELECT * FROM `accounts` WHERE `name` = '" + account + "';");
			while(result.next()){
					return result.getDouble("money");
			}
			return null;
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
	public void transfer(String account1, String account2, Double money) throws BankAccountException {
		Connection conn = connectDB();
		if (conn == null)
			throw new DatabaseConnectException();
		try {
			Double umoney = 0.00;
			Statement stmt = conn.createStatement();
			ResultSet umoney2 = stmt.executeQuery("SELECT * FROM `accounts` WHERE `name` = '" + account1 + "';");
			while(umoney2.next()){
					umoney = umoney2.getDouble("money");
			}
			stmt.executeUpdate("UPDATE `accounts` SET  `money`='" + (roundDouble(umoney - money)) + "' WHERE `accounts`.`name` = '" + account1 + "'");
			umoney2 = stmt.executeQuery("SELECT * FROM `accounts` WHERE `name` = '" + account2 + "';");
			while(umoney2.next()){
					umoney = umoney2.getDouble("money");
			}
			stmt.executeUpdate("UPDATE `accounts` SET  `money`='" + (roundDouble(umoney + money)) + "' WHERE `accounts`.`name` = '" + account2 + "'");
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
	public void interest(String account, Double percent) throws BankAccountException {
		Connection conn = connectDB();
		if (conn == null)
			throw new DatabaseConnectException();
		try {
			Double umoney = 0.00;
			Statement stmt = conn.createStatement();
			ResultSet umoney2 = stmt.executeQuery("SELECT * FROM `accounts` WHERE `name` = '" + account + "';");
			while(umoney2.next()){
					umoney = umoney2.getDouble("money");
			}
			stmt.executeUpdate("UPDATE `accounts` SET  `money`='" + (roundDouble(umoney - (umoney * percent))) + "' WHERE `accounts`.`name` = '" + account + "'");
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
	public void addUser(String account, String player, int accesslevel) throws BankAccountException {
		// TODO Auto-generated method stub
		//System.out.println("Alpha");
		throw new DatabaseConnectException();
	}

	@Override
	public void removeUser(String account, String player) throws BankAccountException {
		// TODO Auto-generated method stub
		//System.out.println("Alpha");
		throw new DatabaseConnectException();
	}

}