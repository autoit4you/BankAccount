package me.autoit4you.bankaccount.exceptions;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandPermissionException extends BankAccountException {

	private static final long serialVersionUID = 7424681583632165001L;

    public CommandPermissionException() {
        lang = "general.nopermission";
    }

	public void print(CommandSender sender, String[] args) {
		sender.sendMessage(ChatColor.RED + "You do not have the permissions.");
	}

}
