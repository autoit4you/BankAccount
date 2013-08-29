package de.autoit4you.bankaccount.api;

import de.autoit4you.bankaccount.BankAccount;

public class APIFactory {

    public API getDumpedInstance(BankAccount plugin) {
        return new API(plugin, true);
    }
}
