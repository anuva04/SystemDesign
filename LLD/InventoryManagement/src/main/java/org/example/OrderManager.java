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

        try {
            for(OrderItem item : order.getOrderItems()) {
                Inventory inventory = inventoryManager.getInventory(item.getProduct().getId(), item.getWarehouse().getId());
                if(inventory == null) {
                    System.out.println("Invalid item in order. Rejecting order.");
                    order.setStatus(OrderStatus.REJECTED);
                    return false;
                }

                try {
                    if(inventory.acquireLock(1, TimeUnit.SECONDS)) {
                        try {
                            if(inventory.getQuantity() >= item.getQuantity()) {
                                inventory.reserveStock(order.getOrderId(), item.getQuantity());
                            } else {
                                System.out.println("Failed to reserve stock. Insufficient stock for " + item.getProduct().getName() + " in " + item.getWarehouse().getName());
                                order.setStatus(OrderStatus.REJECTED);
                                return false;
                            }
                        } finally {
                            inventory.releaseLock();
                        }
                    } else {
                        System.out.println("Failed to acquire lock for " + item.getProduct().getName() + " in " + item.getWarehouse().getName());
                        order.setStatus(OrderStatus.REJECTED);
                        return false;
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Reservation interrupted. Rolling back.");
                    order.setStatus(OrderStatus.REJECTED);
                    return false;
                }
            }

            System.out.println("Stock successfully reserved for Order ID " + order.getOrderId() + ". Simulating long-running payment process...");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            for(OrderItem item : order.getOrderItems()) {
                Inventory inventory = inventoryManager.getInventory(item.getProduct().getId(), item.getWarehouse().getId());
                if(inventory != null) {
                    try {
                        if(inventory.acquireLock(1, TimeUnit.SECONDS)) {
                            try {
                                int currentStock = inventory.getQuantity();
                                inventory.setQuantity(currentStock - item.getQuantity());
                                inventory.releaseReservedStock(order.getOrderId());
                                inventoryManager.getHistoryManager().logTransaction(new Transaction(
                                        item.getProduct().getId(),
                                        item.getWarehouse().getId(),
                                        item.getQuantity(),
                                        TransactionType.ORDER_FULFILLMENT
                                ));
                            } finally {
                                inventory.releaseLock();
                            }
                        } else {
                            System.out.println("Failed to acquire lock for fulfillment. Order " + order.getOrderId() + " may be in inconsistent state.");
                            order.setStatus(OrderStatus.REJECTED);
                            return false;
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("Fulfillment interrupted. Order " + order.getOrderId() + " may be in inconsistent state.");
                        order.setStatus(OrderStatus.REJECTED);
                        return false;
                    }
                }
            }

            order.setStatus(OrderStatus.FULFILLED);
            System.out.println("Order ID " + order.getOrderId() + " successfully fulfilled.");
            return true;
        } finally {
            if(!order.getStatus().equals(OrderStatus.FULFILLED)) {
                System.out.println("Rolling back reservation for Order ID " + order.getOrderId() + ".");
                for (OrderItem item : order.getOrderItems()) {
                    Inventory inventory = inventoryManager.getInventory(item.getProduct().getId(), item.getWarehouse().getId());
                    if (inventory != null) {
                        try {
                            if (inventory.acquireLock(1, TimeUnit.SECONDS)) {
                                try {
                                    Integer reservedQuantity = inventory.getReservedStock().get(order.getOrderId());
                                    if (reservedQuantity != null) {
                                        inventory.setQuantity(inventory.getQuantity() + reservedQuantity);
                                        System.out.println("Rolled back " + reservedQuantity + " units for " + item.getProduct().getName());
                                    }
                                } finally {
                                    inventory.releaseReservedStock(order.getOrderId());
                                    inventory.releaseLock();
                                }
                            }
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
        }
    }
}
