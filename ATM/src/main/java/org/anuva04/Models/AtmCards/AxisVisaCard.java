package org.anuva04.Models.AtmCards;

import org.anuva04.Enums.PaymentNetwork;
import org.anuva04.Models.Bank;

import java.time.LocalDate;

public class AxisVisaCard extends AtmCard {
    public AxisVisaCard(String cardNumber, int CVV, String ownerName, LocalDate expiryDate) {
        super(cardNumber, CVV, ownerName, expiryDate);
        this.setBank(new Bank("Axis Bank"));
        this.setPaymentNetwork(PaymentNetwork.VISA);
    }
}
