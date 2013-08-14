package de.autoit4you.bankaccount.exceptions;

import de.autoit4you.bankaccount.BankAccount;

public abstract class BankAccountException extends Exception {

	private static final long serialVersionUID = -5695575094371183661L;
    String cause = "";
    String lang;

    @Override
    public String getLocalizedMessage() {
        return BankAccount.getLangManager().getLocalString(lang);
    }

    @Override
    public String getMessage() {
        return BankAccount.getLangManager().getString(lang);
    }

    @Override
    public int hashCode() {
        return cause.hashCode() * lang.hashCode();
    }

    public String getCauser() {
        return cause;
    }
}
