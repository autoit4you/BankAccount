package de.autoit4you.bankaccount.api;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.autoit4you.bankaccount.api.event.AccountMoneyEvent;

import org.bukkit.Bukkit;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Account.class)
public class APITest {

    @Test
    public void testAccount() {
        API mockAPI = PowerMockito.mock(API.class);
        Account mockAccount = PowerMockito.mock(Account.class);
        when(mockAccount.getName()).thenReturn("test");
        when(mockAccount.getMoney()).thenReturn(99999.99d);
        when(mockAccount.getApi()).thenReturn(mockAPI);
        ArrayList<Account> mockAccounts = new ArrayList<Account>();
        mockAccounts.add(mockAccount);
        when(mockAPI.getAccounts()).thenReturn(mockAccounts);

        mockAccount.setMoney(mockAccount.getMoney() - 99.9d, TransactionType.PLUGIN);
        verify(Bukkit.getPluginManager()).callEvent(any(AccountMoneyEvent.class));
    }
}
