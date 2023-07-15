package org.anuva04.Controllers;

import org.anuva04.Models.Tray;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Simulates display and input options (keypad) of Vending Machine
public class VendingMachine {
    int numSlots;
    Map<Integer, Tray> inventory;

    public VendingMachine(int numSlots, int traySize) {
        this.numSlots = numSlots;
        inventory = new HashMap<>();
        for(int slot = 1; slot <= numSlots; slot++) {
            inventory.put(slot, new Tray(0, traySize));
        }
    }

    // Simulates home-screen of Vending Machine display
    public void init() {
        while(true) {
            System.out.println("Press 1: User, 2: Admin ");
            Scanner sc = new Scanner(System.in);
            int input = sc.nextInt();
            switch (input) {
                case 1:
                    UserOperations userOperations = new UserOperations(this);
                    userOperations.init();
                    break;
                case 2:
                    AdminOperations adminOperations = new AdminOperations(this);
                    adminOperations.init();
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
}
