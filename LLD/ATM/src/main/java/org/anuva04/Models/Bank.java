package org.anuva04.Models;

public class Bank {
    private String bankName;

    public Bank(String bankName) {
        this.bankName = bankName;
    }

    public boolean verifyAtmPin(String cardNumber, int pin) {
        // Pin verification logic here
        return true;
    }
}
