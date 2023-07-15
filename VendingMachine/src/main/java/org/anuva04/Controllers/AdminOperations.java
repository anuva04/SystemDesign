package org.anuva04.Controllers;

import org.anuva04.Constants;
import org.anuva04.Models.Item;

import java.util.Scanner;

// Collection of methods available to admins
public class AdminOperations {
    private VendingMachine vm;

    public AdminOperations(VendingMachine vm) {
        this.vm = vm;
    }

    public void init() {
        System.out.println("Enter card in slot");
        // Card entered by admin
        System.out.println("Enter PIN");
        Scanner sc = new Scanner(System.in);
        int pin = sc.nextInt();
        if(pin != Constants.AdminPass) {
            System.out.println("Invalid PIN!");
            return;
        }
        // Provide access
        handleAdminOperation();
    }

    private void handleAdminOperation() {
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println("Enter slot number or 0 to exit");
            int slot = sc.nextInt();
            if(slot == 0) {
                break;
            } else if(slot > vm.inventory.size() || slot < 0){
                System.out.println("Invalid slot number!");
                break;
            }
            vm.inventory.get(slot).removeAllItems();

            System.out.println("Enter item name: ");
            String itemName = sc.next();
            System.out.println("Enter item price: ");
            int itemPrice = sc.nextInt();
            System.out.println("Enter item quantity: ");
            int itemQuantity = sc.nextInt();

            vm.inventory.get(slot).setPrice(itemPrice);

            for(int i = 1; i <= itemQuantity; i++) {
                boolean status = vm.inventory.get(slot).addItem(new Item(itemName, itemPrice));
                if(!status) {
                    System.out.println("Maximum capacity reached.");
                    break;
                }
            }

            System.out.println("Items added to slot " + slot + " successfully!");
        }
    }
}
