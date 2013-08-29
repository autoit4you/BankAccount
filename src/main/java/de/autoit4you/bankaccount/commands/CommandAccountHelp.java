package de.autoit4you.bankaccount.commands;

import java.util.List;

import de.autoit4you.bankaccount.BankAccount;
import de.autoit4you.bankaccount.exceptions.*;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandAccountHelp extends BankAccountCommand {
	
	@Override
	public void run(CommandSender sender, String[] args, BankAccount plugin) throws BAArgumentException, CommandPermissionException{
		if(!plugin.getPermissions().user(sender, args))
			throw new CommandPermissionException();
		
		int page = -1;
		
		if(args.length <= 1) {
			page = 1;
		}else {
			try{
				int i = Integer.parseInt(args[1]);
				page = i;
			} catch(NumberFormatException e) {
				sender.sendMessage(ChatColor.RED + "That is not a valid number!");
                return;
			}
		}
		
		sender.sendMessage(ChatColor.GOLD + plugin.getLanguageManager().getLocalString("command.help.top") + " - " + plugin.getLanguageManager().getLocalString("general.page") + page + "/4");

        if(page < 1 || page > 4)
            page = 1;

		if(page == 1) {
			sender.sendMessage("/account help <" + plugin.getLanguageManager().getLocalString("general.page").toUpperCase() +
                    "> - " + plugin.getLanguageManager().getLocalString("command.help.commandHelp"));
            sender.sendMessage("/account top - " + plugin.getLanguageManager().getLocalString("command.help.commandTop"));
            sender.sendMessage("/account list - " + plugin.getLanguageManager().getLocalString("command.help.commandList"));
            sender.sendMessage("/account open <" + plugin.getLanguageManager().getLocalString("general.name").toUpperCase() +
                    "> - " + plugin.getLanguageManager().getLocalString("command.help.commandOpen"));
			sender.sendMessage("/account close <" + plugin.getLanguageManager().getLocalString("general.name").toUpperCase() +
                    "> - " + plugin.getLanguageManager().getLocalString("command.help.commandClose"));
			sender.sendMessage("/account balance <" + plugin.getLanguageManager().getLocalString("general.name").toUpperCase() +
                    "> - " + plugin.getLanguageManager().getLocalString("command.help.commandBalance"));
		}else if(page == 2) {
			sender.sendMessage("/account withdraw <" + plugin.getLanguageManager().getLocalString("general.name").toUpperCase() +
                    "> <" + plugin.getLanguageManager().getLocalString("general.amount").toUpperCase() +
                    "> - " + plugin.getLanguageManager().getLocalString("command.help.commandWithdraw"));
			sender.sendMessage("/account deposit <" + plugin.getLanguageManager().getLocalString("general.name").toUpperCase() +
                    "> <" + plugin.getLanguageManager().getLocalString("general.amount").toUpperCase() +
                    "> - " + plugin.getLanguageManager().getLocalString("command.help.commandDeposit"));
			sender.sendMessage("/account transfer <FROM> <TO> <" + plugin.getLanguageManager().getLocalString("general.amount").toUpperCase() +
                    "> - " + plugin.getLanguageManager().getLocalString("command.help.commandTransfer"));
		}else if(page == 3) {
			sender.sendMessage("/account adduser <" + plugin.getLanguageManager().getLocalString("general.account").toUpperCase() +
                    "> <" + plugin.getLanguageManager().getLocalString("general.name").toUpperCase() +
                    "> - " + plugin.getLanguageManager().getLocalString("command.help.commandAdduser"));
			sender.sendMessage("/account removeuser <" + plugin.getLanguageManager().getLocalString("general.account").toUpperCase() +
                    "> <" + plugin.getLanguageManager().getLocalString("general.name").toUpperCase() +
                    "> - " + plugin.getLanguageManager().getLocalString("command.help.commandRemoveuser"));
			sender.sendMessage("/account addadmin <" + plugin.getLanguageManager().getLocalString("general.account").toUpperCase() +
                    "> <" + plugin.getLanguageManager().getLocalString("general.name").toUpperCase() +
                    "> - " + plugin.getLanguageManager().getLocalString("command.help.commandAddadmin"));
			sender.sendMessage("/account removeadmin <" + plugin.getLanguageManager().getLocalString("general.account").toUpperCase() +
                    "> <" + plugin.getLanguageManager().getLocalString("general.name").toUpperCase() +
                    "> - " + plugin.getLanguageManager().getLocalString("command.help.commandRemoveadmin"));
		}else if(page == 4) {
			sender.sendMessage("/account transferownership <" + plugin.getLanguageManager().getLocalString("general.account").toUpperCase() +
                    "> <" + plugin.getLanguageManager().getLocalString("general.name").toUpperCase() +
                    "> - " + plugin.getLanguageManager().getLocalString("command.help.commandTransferownership"));
			sender.sendMessage(ChatColor.BOLD + plugin.getLanguageManager().getLocalString("general.notice") + ": " +
                    ChatColor.RESET + "" + ChatColor.DARK_RED + plugin.getLanguageManager().getLocalString("command.help.TransferOwnershipNotice1"));
			sender.sendMessage(ChatColor.DARK_RED + plugin.getLanguageManager().getLocalString("command.help.TransferOwnershipNotice2"));
		}
	}

	@Override
	public List<String> tab(CommandSender sender, String[] args) {
		return null;
	}

}
