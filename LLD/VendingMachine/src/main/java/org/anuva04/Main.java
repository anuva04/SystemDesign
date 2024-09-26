package org.anuva04;

import org.anuva04.Controllers.VendingMachine;

public class Main {
    // Method to simulate a physical Vending Machine up and running
    public static void main(String[] args) {
        VendingMachine vm = new VendingMachine(9, 10);
        vm.init();
    }
}