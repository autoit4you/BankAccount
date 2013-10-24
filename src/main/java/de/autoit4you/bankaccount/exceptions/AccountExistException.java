package de.autoit4you.bankaccount.exceptions;

public class AccountExistException extends BankAccountException {

	private static final long serialVersionUID = -8623493378954328995L;
	
	public AccountExistException(int type) {
		switch (type) {
            case 0:
                lang = "exception.accountexist.not";
            case 1:
                lang = "exception.accountexist.already";
        }
	}
}
