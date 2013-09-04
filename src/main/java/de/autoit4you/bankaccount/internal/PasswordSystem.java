package de.autoit4you.bankaccount.internal;

import de.autoit4you.bankaccount.BankAccount;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordSystem {
    private BankAccount plugin;

    public PasswordSystem(BankAccount plugin) {
        this.plugin = plugin;
    }

    /**
     * Based on TATDK's way to encrypt passwords.
     */
    public String hashPassword(String password) {
        byte[] bytePw = password.getBytes();
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(bytePw);
            byte[] hashedBytePw = md.digest();
            char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
            StringBuilder builder = new StringBuilder();
            for(int i=0; i<hashedBytePw.length; i++) {
                builder.append(hexDigit[(hashedBytePw[i] >> 4) & 0x0f]);
                builder.append(hexDigit[hashedBytePw[i] & 0x0f]);
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            plugin.getLogger().severe("[BankAccount] " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
