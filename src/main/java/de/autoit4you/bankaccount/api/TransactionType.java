package de.autoit4you.bankaccount.api;

/**
 * To specify the TransactionType
 * @since 0.4
 */
public enum TransactionType {
    @Deprecated WITHDRAW, @Deprecated DEPOSIT, PLUGIN, UNKNOWN,
    @Deprecated TRANSFER_WITHDRAW, @Deprecated TRANSFER_DEPOSIT
}
