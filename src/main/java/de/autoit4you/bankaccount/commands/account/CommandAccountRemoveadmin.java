package de.autoit4you.bankaccount.commands.account;

import java.util.List;

import de.autoit4you.bankaccount.BankAccount;
import de.autoit4you.bankaccount.Permissions;
import de.autoit4you.bankaccount.api.Account;
import de.autoit4you.bankaccount.commands.BankAccountCommand;
import de.autoit4you.bankaccount.exceptions.*;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandAccountRemoveadmin extends BankAccountCommand {

    @Override
    public void run(CommandSender sender, String[] args, BankAccount plugin)
            throws BAArgumentException, CommandPermissionException {
        if(args.length < 3 || args[1] == null)
            throw new BAArgumentException();

        if(!plugin.getPermissions().user(sender, Permissions.Permission.ACCOUNT_MANAGE_ADMIN))
            throw new CommandPermissionException();

        try {
            Account account = plugin.getAPI().getAccountByName(args[1]);
            if(account.getAccess(sender.getName()) < 3) {
                sender.sendMessage(ChatColor.RED + "You do not have enough permissions for this account!");
            } else {
                account.removeAccess(args[2]);
                sender.sendMessage(ChatColor.GOLD + args[2] + ChatColor.GREEN + " has no longer admin access to the account " + args[1] + "");
            }
        } catch (AccountExistException existe) {
            sender.sendMessage(ChatColor.RED + "Account does not exist!");
        }
    }

	@Override
	public List<String> tab(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}

}
