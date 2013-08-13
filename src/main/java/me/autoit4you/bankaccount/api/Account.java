package me.autoit4you.bankaccount.api;

import me.autoit4you.bankaccount.api.event.*;
import me.autoit4you.bankaccount.exceptions.*;
import org.bukkit.Bukkit;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Account {
    private API api;
    private String name;
    private double money;
    private HashMap<String, Integer> access = new HashMap<String, Integer>();

    public Account(API api, String name) {
        this.name = name;
        this.api = api;
    }

    /**
     * To get the API context to which the account belongs
     * @return The API context of the account
     * @since 0.4
     */
    public API getApi() {
        return api;
    }

    /**
     * Gives the name of the account, the current unique identifier. Might deprecate in the future.
     * @return The account name
     * @since 0.4
     */
    public String getName() {
        return name;
    }

    /**
     * Deposit the specified amount of money to the account. Checks before if the sender has enough permission.
     * @param amount Specifies the amount of money to deposit
     * @param sender The sender to check, if he has permission
     * @throws AccountPermissionException If the sender has not the required permission
     * @since 0.4
     * @deprecated No good coding practice. Use {@link #getMoney()} and {@link #setMoney(double, TransactionType)} instead
     */
    @Deprecated
    public void deposit(double amount, Sender sender) throws AccountPermissionException {
        if(!sender.hasPermissions(Sender.Permission.manageMoney, this))
            throw new AccountPermissionException();

        setMoney(getMoney() + amount, TransactionType.DEPOSIT);
    }

    /**
     * Withdraw the specified amount of money from the account. Checks before if the sender has enough permission.
     * @param amount Specifies the amount of money to deposit
     * @param sender The sender t check, if he has permission
     * @throws AccountPermissionException If the sender has not the required permission
     * @since 0.4
     * @deprecated No good coding practice. Use {@link #getMoney()} and {@link #setMoney(double, TransactionType)} instead
     */
    @Deprecated
    public void withdraw(double amount, Sender sender) throws AccountPermissionException {
        if(!sender.hasPermissions(Sender.Permission.manageMoney, this))
            throw new AccountPermissionException();

        setMoney(getMoney() - amount, TransactionType.WITHDRAW);
    }

    /**
     * Transfer the specified amount of money to the specified account. Checks before if he has enough permissions.
     * @param to The account where the money is being sent
     * @param amount Specifies the amount of money to be transferred
     * @param sender The sender to check, if he has permission
     * @throws AccountPermissionException If the sender has not the required permission
     * @since 0.4
     * @deprecated No good coding practice. Use {@link #getMoney()} and {@link #setMoney(double, TransactionType)} instead
     */
    @Deprecated
    public void transfer(Account to, double amount, Sender sender) throws AccountPermissionException {
        if(!sender.hasPermissions(Sender.Permission.manageMoney, this))
            throw new AccountPermissionException();

        to.setMoney(to.getMoney() + amount, TransactionType.TRANSFER_DEPOSIT);
        setMoney(getMoney() - amount, TransactionType.TRANSFER_WITHDRAW);
    }

    /**
     * To give interest of the specified percent to the account.(1 is 100% and 0 is 0%)
     * @param percent
     * @since 0.4
     */
    public void interest(double percent) {
        setMoney(getMoney() + (getMoney() * percent), TransactionType.PLUGIN);
    }

    public void setMoney(double amount, TransactionType tt) {
        AccountMoneyEvent event = new AccountMoneyEvent(this, amount, tt);
        Bukkit.getPluginManager().callEvent(event);
        if(event.isCancelled())
            return;

        money = roundDouble(amount);
    }

    public double getMoney() {
        return money;
    }

    public HashMap<String, Integer> getAllUsers() {
        return access;
    }

    public void setAllUsers(HashMap<String, Integer> hash) {
        access = hash;
    }

    public String[] getUsers() {
        List<String> users = new ArrayList<String>();
        for(String name : access.keySet()) {
            if(access.get(name) == 1) {
                users.add(name);
            }
        }

        return (String[])(users.toArray());
    }

    public String[] getAdmins() {
        List<String> admins = new ArrayList<String>();
        for(String name : access.keySet()) {
            if(access.get(name) == 2) {
                admins.add(name);
            }
        }

        return (String[])(admins.toArray());
    }

    public String getOwner() {
        List<String> owner = new ArrayList<String>();
        for(String name : access.keySet()) {
            if(access.get(name) == 3) {
                owner.add(name);
            }
        }

        if(owner.size() < 1)
            throw new NullPointerException("No one is Owner");

        return owner.get(0);
    }

    public void setAccess(String name, int level) {
        access.put(name, level);
    }

    public void removeAccess(String name) {
        access.remove(name);
    }

    public int getAccess(String name) {
        if(access.keySet().contains(name))
            return access.get(name);
        else
            return -1;
    }

    //Private function
    private double roundDouble(double number){
        DecimalFormat twoDP = new DecimalFormat("#.##");
        String twoDecimalPlaces = twoDP.format(number);
        return Double.valueOf(twoDecimalPlaces.replace(",", "."));
    }
}
