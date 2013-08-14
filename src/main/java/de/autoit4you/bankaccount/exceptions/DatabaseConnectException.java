package de.autoit4you.bankaccount.exceptions;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class DatabaseConnectException extends BankAccountException {

	private static final long serialVersionUID = -477923169590409572L;

    @Override
    public String getLocalizedMessage() {
        return getMessage();
    }

    @Override
    public String getMessage() {
        return "Cannot connect to database";
    }

    @Override
    public int hashCode() {
        return "Database".hashCode() * "db.connect".hashCode();
    }

    public String getCauser() {
        return "";
    }

	public void print(CommandSender sender, String[] args) {
		Logger.getLogger("Minecraft").severe("Cannot connect to database! Please check the database server!");
		sender.sendMessage(ChatColor.DARK_RED + "An error occurred while performing this command! Please inform the next server administrator!");
	}

}
