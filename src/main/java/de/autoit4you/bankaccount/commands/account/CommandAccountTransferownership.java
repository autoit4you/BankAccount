package de.autoit4you.bankaccount.commands.account;

import java.util.List;

import de.autoit4you.bankaccount.BankAccount;
import de.autoit4you.bankaccount.Permissions;
import de.autoit4you.bankaccount.api.Account;
import de.autoit4you.bankaccount.commands.BankAccountCommand;
import de.autoit4you.bankaccount.exceptions.AccountExistException;
import de.autoit4you.bankaccount.exceptions.BAArgumentException;
import de.autoit4you.bankaccount.exceptions.CommandPermissionException;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandAccountTransferownership extends BankAccountCommand {

    @Override
    public void run(CommandSender sender, String[] args, BankAccount plugin)
            throws BAArgumentException, CommandPermissionException {
        if(args.length < 3 || args[1] == null)
            throw new BAArgumentException();

        if(!plugin.getPermissions().user(sender, Permissions.Permission.ACCOUNT_MANAGE_TRANSFER))
            throw new CommandPermissionException();

        try {
            Account account = plugin.getAPI().getAccountByName(args[1]);
            if(account.getAccess(sender.getName()) < 3) {
                sender.sendMessage(ChatColor.RED + "You do not have enough permissions for this account!");
            } else {
                account.setAccess(args[2], 3);
                account.removeAccess(sender.getName());
                sender.sendMessage(ChatColor.GREEN + "You transferred ownership from you to " + ChatColor.GOLD + args[2] + ChatColor.GREEN + " for the account " + args[1] + "");
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
