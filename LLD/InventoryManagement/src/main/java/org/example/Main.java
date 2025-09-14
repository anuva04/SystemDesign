package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        InventoryManager ims = InventoryManager.getInstance();

        Product tv = new Product("P-001", "4K Smart TV");
        Product laptop = new Product("P-002", "Laptop");
        Product phone = new Product("P-003", "Smartphone");

        Warehouse mainWarehouse = new Warehouse("W-001", "Main Warehouse");
        Warehouse regionalWarehouse = new Warehouse("W-002", "Regional Warehouse");

        ims.registerProduct(tv);
        ims.registerProduct(laptop);
        ims.registerProduct(phone);
        ims.registerWarehouse(mainWarehouse);
        ims.registerWarehouse(regionalWarehouse);

        ims.addInventory(tv.getId(), mainWarehouse.getId(), 50, 10);
        ims.addInventory(laptop.getId(), mainWarehouse.getId(), 30, 5);
        ims.addInventory(phone.getId(), regionalWarehouse.getId(), 100, 20);

        ims.printStockLevels();
        ims.removeStock(tv.getId(), mainWarehouse.getId(), 25);
        ims.addStock(laptop.getId(), mainWarehouse.getId(), 10);
        ims.printStockLevels();

        OrderManager oms = new OrderManager(ims);

        System.out.println("\n--- Starting Multithreaded Order Fulfillment Test ---");

        List<OrderItem> orderItems1 = new ArrayList<>();
        orderItems1.add(new OrderItem(tv, mainWarehouse, 10));
        orderItems1.add(new OrderItem(laptop, mainWarehouse, 5));
        Order order1 = new Order("ORD-001", orderItems1);

        List<OrderItem> orderItems2 = new ArrayList<>();
        orderItems2.add(new OrderItem(tv, mainWarehouse, 15));
        Order order2 = new Order("ORD-002", orderItems2);

        Thread thread1 = new Thread(() -> {
            oms.createAndFulfillOrder(order1);
        }, "Order-Thread-1");
        Thread thread2 = new Thread(() -> {
            oms.createAndFulfillOrder(order2);
        }, "Order-Thread-2");

        thread1.start();
        Thread.sleep(100);
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("\n--- Multithreaded Test Complete ---");
        ims.printStockLevels();

        ims.setMinStockThreshold(laptop.getId(), mainWarehouse.getId(), 40);
        ims.removeStock(laptop.getId(), mainWarehouse.getId(), 1);
        ims.printTransactionHistory();

        List<OrderItem> orderItems3 = new ArrayList<>();
        orderItems3.add(new OrderItem(tv, mainWarehouse, 50));
        Order order3 = new Order("ORD-003", orderItems3);
        oms.createAndFulfillOrder(order3);
        ims.printStockLevels();
    }
}