package me.autoit4you.bankaccount.api.event;

import me.autoit4you.bankaccount.api.Account;
import me.autoit4you.bankaccount.api.TransactionType;
import org.bukkit.event.Cancellable;

/**
 * Project: BankAccount
 * Author: autoit4you
 */
public class AccountMoneyEvent extends BAEvent implements Cancellable {
    private boolean cancelled = false;
    private Account account;
    private double amount;
    private TransactionType tt;

    public AccountMoneyEvent(Account account, double amount, TransactionType tt) {
        this.account = account;
        this.amount = amount;

        //if(tt != TransactionType.DEPOSIT && tt != TransactionType.WITHDRAW && tt != TransactionType.TRANSFER_DEPOSIT && tt != TransactionType.TRANSFER_WITHDRAW)
        //    throw new UnsupportedOperationException();

        this.tt = tt;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return tt;
    }

    public void setTransactionType(TransactionType tt) {
        //if(tt != TransactionType.DEPOSIT && tt != TransactionType.WITHDRAW && tt != TransactionType.TRANSFER_DEPOSIT && tt != TransactionType.TRANSFER_WITHDRAW)
        //    throw new UnsupportedOperationException();

        this.tt = tt;
    }
}
