package me.autoit4you.bankaccount.exceptions;

public class AccountPermissionException extends BankAccountException {

    public AccountPermissionException() {
        lang = "account.permission.no";
    }
}
