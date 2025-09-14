package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OrderManager {
    private InventoryManager inventoryManager;

    public OrderManager(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    public boolean createAndFulfillOrder(Order order) {
        System.out.println("\nProcessing Order ID: " + order.getOrderId());
        List<Inventory> lockedItems = new ArrayList<>();
        boolean success = false;

        try {
            for(OrderItem item : order.getOrderItems()) {
                String inventoryKey = item.getProduct().getId() + ":" + item.getWarehouse().getId();
                Inventory inventory = inventoryManager.getInventory(item.getProduct().getId(), item.getWarehouse().getId());

                if(inventory != null && inventory.acquireLock(1, TimeUnit.SECONDS) && inventory.getQuantity() >= item.getQuantity()) {
                    lockedItems.add(inventory);
                } else {
                    System.out.println("Failed to acquire lock or insufficient stock for " + item.getProduct().getName() + " in " + item.getWarehouse().getName());
                    order.setStatus(OrderStatus.REJECTED);
                    return false;
                }
            }

            System.out.println("All stock availability verified. Fulfilling order...");

            // Simulate a long-running process to create a race condition
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            for(OrderItem item : order.getOrderItems()) {
                inventoryManager.removeStock(item.getProduct().getId(), item.getWarehouse().getId(), item.getQuantity());
            }

            order.setStatus(OrderStatus.FULFILLED);
            System.out.println("Order ID " + order.getOrderId() + " successfully fulfilled.");
            success = true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Order fulfillment interrupted. Rolling back.");
            order.setStatus(OrderStatus.REJECTED);
            success = false;
        } finally {
            for(Inventory inv : lockedItems) inv.releaseLock();
        }
        return success;
    }
}
