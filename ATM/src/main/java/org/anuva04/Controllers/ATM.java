package org.anuva04.Controllers;

import org.anuva04.Models.AtmCards.AtmCard;
import org.anuva04.Models.CashReserve;
import org.anuva04.Strategies.CashDispenserStrategy;

import java.util.Scanner;
import java.util.UUID;

public class ATM {
    private UUID id;
    private CashReserve cashReserve;
    private CashDispenserStrategy cashDispenserStrategy;

    public ATM() {
        cashReserve = new CashReserve();
        this.id = UUID.randomUUID();
    }

    // Method to set cash dispensing strategy
    public void setCashDispenserStrategy(CashDispenserStrategy cashDispenserStrategy) {
        this.cashDispenserStrategy = cashDispenserStrategy;
    }

    // Method to handle user requests after ATM card is inserted and PIN is verified
    public void startTransaction(AtmCard atmCard) {
        Scanner sc = new Scanner(System.in);
        Transaction transaction = new Transaction();
        if (!transaction.verifyPin(atmCard)) {
            System.out.println("Wrong PIN!!!");
            return;
        }
        while (true) {
            System.out.println("Choose an option: ");
            System.out.println(
                    "Withdraw cash: 1\n" +
                            "Change Pin: 2\n" +
                            "Check balance: 3\n" +
                            "Get mini statement: 4\n" +
                            "Exit: 5");
            int option = sc.nextInt();
            boolean exit = false;
            switch (option) {
                case 1:
                    transaction.withdraw(cashReserve, cashDispenserStrategy);
                    break;
                case 2:
                    transaction.changePin(atmCard);
                    break;
                case 3:
                    transaction.checkBalance(atmCard);
                    break;
                case 4:
                    transaction.getMiniStatement(atmCard);
                    break;
                case 5:
                    System.out.println("Thank you for visiting!!!");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option!!!");
            }
            if (exit) break;
        }
    }

    // Method to update data about cash reserve in ATM
    public void modifyCashReserve(CashReserve newCashReserve) {
        this.cashReserve.modifyCashReserve(newCashReserve);
        System.out.println("Cash reserve modified successfully!");
    }

    // Method to verify admin login
    public boolean adminLogin(String password) {
        if (password.equalsIgnoreCase("admin12345")) return true;
        return false;
    }
}
