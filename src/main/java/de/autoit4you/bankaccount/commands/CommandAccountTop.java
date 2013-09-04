package de.autoit4you.bankaccount.commands;

import de.autoit4you.bankaccount.BankAccount;
import de.autoit4you.bankaccount.Permissions;
import de.autoit4you.bankaccount.api.Account;
import de.autoit4you.bankaccount.exceptions.BAArgumentException;
import de.autoit4you.bankaccount.exceptions.CommandPermissionException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CommandAccountTop extends BankAccountCommand {
    @Override
    public void run(CommandSender sender, String[] args, BankAccount plugin)
            throws BAArgumentException, CommandPermissionException {
        if(!plugin.getPermissions().user(sender, Permissions.Permission.ACCOUNT_TOP))
            throw new CommandPermissionException();

        sender.sendMessage(ChatColor.GOLD + plugin.getLanguageManager().getLocalString("command.account.top"));

        int i = 0;
        for(Account acc : plugin.getAPI().getTopAccount()) {
            sender.sendMessage(i + ". " + acc.getName());
        }
    }

    @Override
    public List<String> tab(CommandSender sender, String[] args) {
        return null;
    }
}
