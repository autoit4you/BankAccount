package me.autoit4you.bankaccount;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

public class Database {

	private final String host;
	private final String database;
	private final String user;
	private final String pwd;

	public Database(String host, String database, String user, String pwd)
			throws Exception {
		super();
		this.host = host;
		this.database = database;
		this.user = user;
		this.pwd = pwd;
		Class.forName("com.mysql.jdbc.Driver");
		if (connectDB() == null)
			throw new Exception("Database not accesible!");
	}

	public Connection connectDB() {
		try {

			return DriverManager.getConnection("jdbc:mysql://" + host + "/"
					+ database, user, pwd);

		} catch (Exception ex) {
			return null;
		}
	}

	public boolean checkmoney(String account, double money) {
		Connection conn = connectDB();
		if (conn == null)
			return false;
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
			e.printStackTrace();
			return false;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {

			}
		}
	}
	public boolean existAccount(String account){
		Connection conn = connectDB();
		if (conn == null)
			return false;
		try {
			Statement stmt = conn.createStatement();
			final ResultSet result = stmt.executeQuery("SELECT * FROM `accounts` WHERE `name` = '" + account + "';");
			return result.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {

			}
		}
	}
	
	public boolean createAccount(String user, String account){
		Connection conn = connectDB();
		if (conn == null)
			return false;
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("INSERT INTO `accounts` (`name`, `money`)  " +
					"VALUES ('" + account +"', '0.00');");
			stmt.executeUpdate("INSERT INTO `access` (`account`, `name`, `accesstype`) " +
					"VALUES ('" + account +"', '" + user +"', '2');");	
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {

			}
		}
	}
	
	public boolean removeAccount(String user, String account){
		Connection conn = connectDB();
		if (conn == null)
			return false;
		try {
			Statement stmt = conn.createStatement();
			final ResultSet result = stmt.executeQuery("SELECT `accesstype` FROM `access` WHERE `account` = '" + account + "' AND `name` = '" + user + "';");
			if(!result.next()){ return false; }
			if(result.getInt("accesstype") == 2){
				stmt.executeUpdate("DELETE FROM `access` WHERE `account` = '" + account + "' AND `name` = '" + user +"' AND `accesstype` = 2");
				stmt.executeUpdate("DELETE FROM `accounts` WHERE `name` = '" + account + "'");
				return true;
			}else if(result.getInt("accesstype") == 1){
				stmt.executeUpdate("DELETE FROM `access` WHERE `account` = '" + account + "' AND `name` = '" + user +"' AND `accesstype` = 1");
				return true;
			}else{
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {

			}
		}
	}
	
	public String getAccounts(String user){
		String temp = "";
		String returnResult = "";
		Connection conn = connectDB();
		if(conn == null)
			return "An error occured while getting the bankaccounts!";
		try{
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery("SELECT `account` FROM `access` WHERE `name` = '" + user + "' AND `accesstype` = '2'");
			while(result.next()){
				if(result.getString("account") != "null"){
						temp = temp + result.getString("account") + ",";
				}
			}
			if(temp != ""){
				returnResult = "Owner: " + temp + "\n";
			}
			temp = "";
			result = stmt.executeQuery("SELECT `account` FROM `access` WHERE `name` = '" + user + "' AND `accesstype` = '1'");
			while(result.next()){
				if(result.getString("account") != "null"){
					temp = temp + result.getString("account") + ",";
				}
			}
			if(temp != ""){
				returnResult = returnResult + "User: " + temp;
			}
			return returnResult;
		} catch (SQLException e){
			e.printStackTrace();
			return "An error occured while getting the bankaccounts! Check the console for more information!";
		} finally {
			try{
				conn.close();
			} catch (SQLException e){
				
			}
		}
	}
	
	public boolean depositMoney(String account, Double money){
		Connection conn = connectDB();
		if (conn == null)
			return false;
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
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {

			}
		}
	}
	
	public boolean withdrawMoney(String account, Double money){
		Connection conn = connectDB();
		if (conn == null)
			return false;
		try {
			Double umoney = 0.00;
			Statement stmt = conn.createStatement();
			final ResultSet umoney2 = stmt.executeQuery("SELECT * FROM `accounts` WHERE `name` = '" + account + "';");
			while(umoney2.next()){
					umoney = umoney2.getDouble("money");
			}
			stmt.executeUpdate("UPDATE `accounts` SET  `money`='" + (roundDouble(umoney - money)) + "' WHERE `accounts`.`name` = '" + account + "'");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {

			}
		}
	}
	
	public int getRights(String account, String player){
		if(account.isEmpty())
			return 0;
		Connection conn = connectDB();
		if (conn == null)
			return 0;
		try {
			Statement stmt = conn.createStatement();
			final ResultSet result = stmt.executeQuery("SELECT * FROM `access` WHERE `account` = '" + account + "' AND `name` = '" + player + "';");
			while(result.next()){
					return result.getInt("accesstype");
			}
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {

			}
		}
	}
	
	public Double getBalance(String account){
		Connection conn = connectDB();
		if (conn == null)
			return null;
		try {
			Statement stmt = conn.createStatement();
			final ResultSet result = stmt.executeQuery("SELECT * FROM `accounts` WHERE `name` = '" + account + "';");
			while(result.next()){
					return result.getDouble("money");
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {

			}
		}
	}
	
	public boolean transfer(String account1, String account2, Double money){
		Connection conn = connectDB();
		if (conn == null)
			return false;
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
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {

			}
		}
	}
	
	//Private functions
	private double roundDouble(double number){
		DecimalFormat twoDP = new DecimalFormat("#.##");
		return Double.valueOf(twoDP.format(number));
	}
}