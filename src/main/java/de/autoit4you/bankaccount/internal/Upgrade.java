package de.autoit4you.bankaccount.internal;

import de.autoit4you.bankaccount.BankAccount;
import de.autoit4you.bankaccount.internal.upgrade.IntroUpgrade;

public abstract class Upgrade {

    public abstract void performUpgrade() throws Throwable;

    public static int getCurrentVersion() {
        return 1;
    }

    public static Upgrade getUpgrade(int version, BankAccount plugin) {
        if(version < 1) {
            return new IntroUpgrade(plugin);
        }
        return null;
    }
}
