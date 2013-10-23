package de.autoit4you.bankaccount.commands;

import de.autoit4you.bankaccount.CommandFactory;
import de.autoit4you.bankaccount.commands.account.CommandAccountOpen;
import org.bukkit.entity.Player;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class TestAccountCommandFromPlayer {

    @Test
    public void open() {
        Player p = mock(Player.class);
        when(p.hasPermission(anyString())).thenReturn(true);
        when(p.getName()).thenReturn("Player");
        String[] args = new String[] {"open"};
        CommandFactory.execute(new CommandAccountOpen(), p, args);

        String[] args2 = new String[] {"open", "test"};
        CommandFactory.execute(new CommandAccountOpen(), p, args2);
    }
}
