package org.anuva04.Models.AtmCards;

import org.anuva04.Enums.PaymentNetwork;
import org.anuva04.Models.Bank;

import java.time.LocalDate;
public class SbiRupayCard extends AtmCard {
    public SbiRupayCard(String cardNumber, int CVV, String ownerName, LocalDate expiryDate) {
        super(cardNumber, CVV, ownerName, expiryDate);
        this.setBank(new Bank("State Bank of India"));
        this.setPaymentNetwork(PaymentNetwork.RUPAY);
    }
}
