// TODO: user can cancel transaction anytime before item is dispensed

package org.anuva04.Controllers;

import java.util.Scanner;

import org.anuva04.Constants;

// Collection of methods available to users
public class UserOperations {
    VendingMachine vm;

    public UserOperations(VendingMachine vm) {
        this.vm = vm;
    }
    public void init(){
        System.out.println("Displaying all items");
        for(int slot = 1; slot <= vm.numSlots; slot++){
            if(vm.inventory.get(slot).peekItem() == null) continue;
            System.out.println("Slot: " + slot +
                    " | price: " + vm.inventory.get(slot).getPrice() +
                    " | item: " + vm.inventory.get(slot).peekItem().name +
                    " | quantity: " + vm.inventory.get(slot).getItemsQuantity()
            );
        }
        System.out.println("Enter slot number or 0 for exit ");
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        if(input == 0){
            System.out.println("Thank you for visiting!");
        } else if(input >= 1 && input <= vm.numSlots) {
            handleTransaction(input);
        } else {
            System.out.println("Invalid option!");
        }
    }

    private void handleTransaction(int slot) {
        if(vm.inventory.get(slot).peekItem() == null) {
            System.out.println("Slot is empty!");
            return;
        }

        if(!handleMoneyTransaction(slot)) return;

        // Dispense item
        System.out.println("Please collect item: " + vm.inventory.get(slot).dispenseItem().name);
        System.out.println(Constants.EmergencyMessage);
    }

    private boolean handleMoneyTransaction(int slot) {
        System.out.println("Please enter amount: " + vm.inventory.get(slot).getPrice());

        // User can pay via cash or card
        Scanner sc = new Scanner(System.in);
        int amount = sc.nextInt();
        if(amount == vm.inventory.get(slot).getPrice()) {
            return true;
        } else if (amount < vm.inventory.get(slot).getPrice()) {
            System.out.println("Amount entered is less than price of product.");
            return false;
        } else {
            // library methods to handle CashDispenserStrategy
            // similar to ATM system
            System.out.println("Please collect change: " + (amount - vm.inventory.get(slot).getPrice()));
            return true;
        }
    }
}
