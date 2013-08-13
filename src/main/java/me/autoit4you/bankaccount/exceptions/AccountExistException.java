package me.autoit4you.bankaccount.exceptions;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class AccountExistException extends BankAccountException {

	private static final long serialVersionUID = -8623493378954328995L;
	
	public AccountExistException(int type) {
		switch (type) {
            case 0:
                lang = "account.exist.not";
            case 1:
                lang = "account.exist.already";
        }
	}
}
