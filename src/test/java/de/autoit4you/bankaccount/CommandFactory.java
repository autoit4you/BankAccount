package de.autoit4you.bankaccount;

import de.autoit4you.bankaccount.api.API;
import de.autoit4you.bankaccount.api.APIFactory;
import de.autoit4you.bankaccount.commands.BankAccountCommand;
import de.autoit4you.bankaccount.exceptions.BankAccountException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

import java.lang.reflect.Field;
import java.util.HashSet;

import static org.mockito.Mockito.*;

public class CommandFactory {

    public static void execute(BankAccountCommand cmd, CommandSender sender, String[] args) {
        BankAccount ba = mock(BankAccount.class);
        when(ba.getPermissions()).thenReturn(new Permissions());
        when(ba.getAPI()).thenReturn(new APIFactory().getDumpedInstance(ba));
        Database db = mock(Database.class);

        try {
            HashSet<String> s = new HashSet<>();
            when(db.getAccounts()).thenReturn(s);
            when(ba.getDB()).thenReturn(db);
            cmd.run(sender, args, ba);
        } catch (BankAccountException e){

        }
    }
}
