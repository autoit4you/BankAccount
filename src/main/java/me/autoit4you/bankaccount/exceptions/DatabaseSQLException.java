package me.autoit4you.bankaccount.exceptions;

import me.autoit4you.bankaccount.BankAccount;

import org.bukkit.command.CommandSender;

public class DatabaseSQLException extends BankAccountException {

	private static final long serialVersionUID = -1616452545234807124L;
	private String msg;
	
	public DatabaseSQLException(String msg) {
		this.msg = msg;
	}

    @Override
    public String getLocalizedMessage() {
        return getMessage();
    }

    @Override
    public String getMessage() {
        return msg;
    }

    @Override
    public int hashCode() {
        return "Database".hashCode() * "db.sqlexception".hashCode() * msg.hashCode();
    }

    public String getCauser() {
        return "";
    }

	public void print(CommandSender sender, String[] args) {
		BankAccount.log.severe(msg);
	}

}
