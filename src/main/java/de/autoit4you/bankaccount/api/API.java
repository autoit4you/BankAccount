package de.autoit4you.bankaccount.api;

import de.autoit4you.bankaccount.BankAccount;
import de.autoit4you.bankaccount.exceptions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class API {
    private List<Account> accounts = null;
    private BankAccount plugin;

    public API(BankAccount plugin) {
        this.plugin = plugin;
        reloadAccounts(false);
    }

    /**
     * To load the accounts from the database(and possibly save it before loading).
     * @param renew If the accounts List should be renewed from the database or the data should only be read from the database.
     * @since 0.4
     */
    public void reloadAccounts(boolean renew) {
        try {

            HashMap<String, String> accountList = plugin.getDB().getAllAccounts();
            List<Account> accounts = new ArrayList<Account>();

            for(String account : accountList.keySet()) {
                Account acc = new Account(this,account);
                plugin.getDB().getRights(account);
                accounts.add(acc);
            }

            this.accounts = accounts;

            plugin.getLogger().info("[BankAccount] " + plugin.getLanguageManager().getLocalString("account.reload.success"));
        } catch (DatabaseConnectException | DatabaseSQLException e) {
            plugin.getLogger().severe("[BankAccount] " + plugin.getLanguageManager().getLocalString("account.reload.fail"));
            plugin.getLogger().severe("[BankAccount] " + e.getLocalizedMessage());
        }
    }

    /**
     * To get a bank account by its name.
     * @param name The name of the account you want to get
     * @return The account with the specified name
     * @throws AccountExistException If no account with that name exits
     * @since 0.4
     */
    public Account getAccountByName(String name) throws AccountExistException {
        for(Account account : getAccounts()) {
            if(account.getName().equals(name))
                return account;
        }
        throw new AccountExistException(0);
    }

    /**
     * Get all accounts that exist.
     * @return All accounts
     * @since 0.4
     */
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * Get the Top 5 accounts by money. 0 is the account with the highest amount of money.
     * If less than 5 accounts exist the undefined accounts are null
     * @return Five accounts with the highest amount of money
     * @since 0.4
     */
    public Account[] getTopAccount() {
        Account[] account = new Account[5];

        for(Account acc : accounts) {
            if(account[0] == null) {
                account[0] = acc;
                continue;
            }

            if(account[0].getMoney() < acc.getMoney()) {
                account[4] = account[3];
                account[3] = account[2];
                account[2] = account[1];
                account[1] = account[0];
                account[0] = acc;
                continue;
            } else {
                if(account[1] == null) {
                    account[1] = acc;
                    continue;
                }

                if(account[1].getMoney() < acc.getMoney()) {
                    account[4] = account[3];
                    account[3] = account[2];
                    account[2] = account[1];
                    account[1] = acc;
                    continue;
                } else {
                    if(account[2] == null) {
                        account[2] = acc;
                        continue;
                    }

                    if(account[2].getMoney() < acc.getMoney()) {
                        account[4] = account[3];
                        account[3] = account[2];
                        account[2] = acc;
                        continue;
                    } else {
                        if(account[3] == null) {
                            account[3] = acc;
                            continue;
                        }

                        if(account[3].getMoney() < acc.getMoney()) {
                            account[4] = account[3];
                            account[3] = acc;
                            continue;
                        } else {
                            if(account[4] == null) {
                                account[4] = acc;
                                continue;
                            }

                            if(account[4].getMoney() < acc.getMoney()) {
                                account[4] = acc;
                                continue;
                            }
                        }
                    }
                }
            }

        }

        return account;
    }

    /**
     * To open a account with the specified name.
     * @param name The name for the account
     * @return The just opened account
     * @throws AccountExistException If another account with that name already exists
     * @since 0.4
     */
    public Account open(String name) throws AccountExistException {
        if(getAccountByName(name) != null)
            throw new AccountExistException(1);

        Account account = new Account(this, name);
        account.setMoney(0.00, TransactionType.PLUGIN);
        accounts.add(account);
        return account;
    }

    /**
     * To close a account with the specified name.
     * @param name The name of the account
     * @throws AccountExistException If no account with the specified name exists
     * @since 0.4
     */
    public void close(String name) throws AccountExistException {
        Account account = getAccountByName(name);
        accounts.remove(account);
    }
}
