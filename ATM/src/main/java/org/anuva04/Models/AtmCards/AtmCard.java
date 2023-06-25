package org.anuva04.Models.AtmCards;

import org.anuva04.Enums.PaymentNetwork;
import org.anuva04.Models.Bank;

import java.time.LocalDate;

public class AtmCard {
    private String cardNumber;
    private int CVV;
    private PaymentNetwork paymentNetwork;
    private String ownerName;
    private LocalDate expiryDate;
    private Bank bank;

    public AtmCard(String cardNumber, int CVV, String ownerName, LocalDate expiryDate) {
        this.cardNumber = cardNumber;
        this.CVV = CVV;
        this.ownerName = ownerName;
        this.expiryDate = expiryDate;
    }

    public void setPaymentNetwork(PaymentNetwork paymentNetwork) {
        this.paymentNetwork = paymentNetwork;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Bank getBank() {
        return this.bank;
    }

    public String getCardNumber() {
        return cardNumber;
    }
}
