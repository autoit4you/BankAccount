package de.autoit4you.bankaccount.exceptions;

import org.bukkit.command.CommandSender;

public class AccountOtherRights extends BankAccountException {

	private static final long serialVersionUID = 4496916336690626497L;
	
	public AccountOtherRights(int access, String user) {
        cause = user;
        switch(access) {
            case 1:
                lang = "account.perm.isUser";
            case 2:
                lang = "account.perm.isAdmin";
            case 3:
                lang = "account.perm.isOwner";
        }

	}


	public void print(CommandSender sender, String[] args) {
		/*sender.sendMessage(ChatColor.RED + "The user " + this.user + "  already has permissions for that account!");
		
		if(this.access == 1) {
			sender.sendMessage(ChatColor.RED + "He is currently account user.");
		}else if(this.access == 2) {
			sender.sendMessage(ChatColor.RED + "He is currently account admin.");
		}else if(this.access == 3) {
			sender.sendMessage(ChatColor.RED + "He is currently account owner.");
		}*/
	}

}
