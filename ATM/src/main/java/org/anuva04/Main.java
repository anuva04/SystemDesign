package org.anuva04;

import org.anuva04.Controllers.ATM;
import org.anuva04.Enums.Denomination;
import org.anuva04.Models.AtmCards.AtmCard;
import org.anuva04.Models.AtmCards.SbiRupayCard;
import org.anuva04.Models.CashReserve;
import org.anuva04.Strategies.SimpleCashDispenseStrategy;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    // Method to simulate insertion of cash in ATM
    public static void handleModifyCashReserve(ATM atm) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter details to modify cash reserve... Enter q to exit: ");
        CashReserve request = new CashReserve();
        while (true) {
            System.out.print("Enter denomination type (TWO_THOUSAND, THOUSAND, FIVE_HUNDRED, HUNDRED, FIFTY) or \"q\" to exit: ");
            String input = sc.next();
            if (input.equalsIgnoreCase("q")) {
                break;
            }
            Denomination denomination = Denomination.valueOf(input.toUpperCase());
            System.out.println("Enter count: ");
            Integer count = sc.nextInt();
            try {
                request.updateCash(denomination, count);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        atm.modifyCashReserve(request);
    }

    // Running main method means ATM is up and running
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.setCashDispenserStrategy(new SimpleCashDispenseStrategy());
        Scanner sc = new Scanner(System.in);

        while(true){
            System.out.println("User login: 1 \nAdmin login: 2");
            int loginType = sc.nextInt();
            switch(loginType){
                case 1:
                    // ATM card insertion stage
                    AtmCard dummyAtmCard = new SbiRupayCard("1234321", 999, "Anuva", LocalDate.now());
                    atm.startTransaction(dummyAtmCard);
                    break;
                case 2:
                    System.out.print("Enter admin password: ");
                    String input = sc.next();
                    if (atm.adminLogin(input)) {
                        System.out.println("Admin Logged in successfully!");
                        handleModifyCashReserve(atm);
                    } else {
                        System.out.println("Invalid password!");
                    }
                    break;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }

    }
}