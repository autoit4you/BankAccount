package me.autoit4you.bankaccount.commands;

import me.autoit4you.bankaccount.BankAccount;
import me.autoit4you.bankaccount.api.Account;
import me.autoit4you.bankaccount.exceptions.BAArgumentException;
import me.autoit4you.bankaccount.exceptions.BankAccountException;
import me.autoit4you.bankaccount.exceptions.CommandPermissionException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Project: BankAccount
 * Author: autoit4you
 */
public class CommandAccountTop extends BankAccountCommand {
    @Override
    public void run(CommandSender sender, String[] args, BankAccount plugin)
            throws BAArgumentException, CommandPermissionException {
        if(!BankAccount.perm.user(sender, args))
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
