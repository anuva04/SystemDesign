package org.anuva04.Controllers;

import org.anuva04.Models.AtmCards.AtmCard;
import org.anuva04.Models.CashAvailabilityDataResponseModel;
import org.anuva04.Models.CashReserve;
import org.anuva04.Strategies.CashDispenserStrategy;

import java.util.Scanner;

// Contains all facilities provided by ATM to users
public class Transaction {
    Scanner sc = new Scanner(System.in);

    // Method to withdraw cash from ATM
    public void withdraw(CashReserve cashReserve, CashDispenserStrategy cashDispenserStrategy) {
        System.out.println("Enter amount: ");
        int amount = sc.nextInt();
        // Contact bank to check user's balance
        // If not enough balance, cancel transaction
        // else
        CashAvailabilityDataResponseModel response = cashDispenserStrategy.getCashAvailability(cashReserve, amount);
        if (!response.isCashAvailable) {
            System.out.println("Cash not available!");
            return;
        }
        // Contact bank to deduct balance
        CashReserve cash = cashDispenserStrategy.dispenseCash(cashReserve, response.cashReserve);
        if (cash == null) {
            System.out.println("Something went wrong! Please contact XXXX");
            // Contact bank to initiate refund
        } else {
            System.out.println("Please collect cash!");
        }
    }

    // Method to change ATM card PIN
    public void changePin(AtmCard atmCard) {
        System.out.println("Enter new PIN: ");
        int newPin = sc.nextInt();
        // Contact bank to change PIN
        System.out.println("Pin changed successfully!");
    }

    // Method to check balance of bank account linked to given ATM card
    public void checkBalance(AtmCard atmCard) {
        // Contact bank to check balance
        System.out.println("Balance for account linked to card " + atmCard.getCardNumber() + " is YY");
    }

    // Method to get Mini-statement of bank account linked to given ATM card
    public void getMiniStatement(AtmCard atmCard) {
        // Contact bank to get mini-statement
        System.out.println("Mini-statement: ###########");
    }

    // Method to verify ATM card PIN
    public boolean verifyPin(AtmCard atmCard) {
        System.out.println("Please enter PIN: ");
        int pin = sc.nextInt();

        // PIN verification process
        return atmCard.getBank().verifyAtmPin(atmCard.getCardNumber(), pin);
    }
}
