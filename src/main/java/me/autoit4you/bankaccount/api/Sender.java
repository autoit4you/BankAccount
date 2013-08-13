package me.autoit4you.bankaccount.api;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

/**
 * Project: BankAccount
 * Author: autoit4you
 */
public class Sender {
    private CommandSender sender;

    public Sender(CommandSender sender) {
        this.sender = sender;
    }

    public boolean hasPermissions(Permission perm, Account acc) {
        if(sender.equals(Bukkit.getConsoleSender())) {
            return true;
        }

        String accessu = null;
        int accesslevel = -1;
        for(String name : acc.getAllUsers().keySet()) {
            if(name.equals(sender.getName()))
                accessu = name;
                accesslevel = acc.getAllUsers().get(name);
        }
        if(accessu == null)
            return false;


        switch(perm) {
            case read:
                return true;

            case manageMoney:
                if(accesslevel > 1)
                    return true;
                else
                    return false;

            case manageUsers:
                if(accesslevel > 1)
                    return true;
                else
                    return false;

            case owner:
                if (accesslevel > 3)
                    return true;
                else
                    return false;

            default:
                throw new EnumConstantNotPresentException(Permission.class, perm.toString());
        }
    }

    public enum Permission{
        manageMoney, read, manageUsers, owner
    }
}
