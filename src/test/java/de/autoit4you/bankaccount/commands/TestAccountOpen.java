package de.autoit4you.bankaccount.commands;

import de.autoit4you.bankaccount.CommandFactory;
import org.bukkit.entity.Player;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class TestAccountOpen {

    @Test
    public void testForExceptionPlayer() {
        Player p = mock(Player.class);
        when(p.hasPermission(anyString())).thenReturn(true);
        when(p.getName()).thenReturn("Player");
        String[] args = new String[] {"open"};
        CommandFactory.execute(new CommandAccountOpen(), p, args);
    }
}
