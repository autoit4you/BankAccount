package de.autoit4you.bankaccount.tasks;

import de.autoit4you.bankaccount.BankAccount;
import org.bukkit.scheduler.BukkitRunnable;

public class AccountsUpdate extends BukkitRunnable {
    private BankAccount plugin;

    public AccountsUpdate(BankAccount plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        plugin.getLogger().info("[BankAccount] Accounts will now be saved to the database...");
        plugin.getAPI().saveData();
        plugin.getLogger().info("[BankAccount] Accounts saved to the database. Clearing cache...");
        plugin.getLogger().info("[BankAccount] Getting all accounts back from the database...");
        plugin.getAPI().reloadAccounts();
        plugin.getLogger().info("[BankAccount] Finish reloading accounts.");
    }
}
