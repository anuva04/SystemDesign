package org.example;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class InventoryManager {
    private static volatile InventoryManager instance;
    private final Map<String, Product> products = new ConcurrentHashMap<>();
    private final Map<String, Warehouse> warehouses = new ConcurrentHashMap<>();
    private final Map<String, Inventory> inventoryMap = new ConcurrentHashMap<>();
    private final TransactionHistoryManager historyManager = new TransactionHistoryManager();

    private InventoryManager() {}

    public static InventoryManager getInstance() {
        if(instance == null) {
            synchronized (InventoryManager.class) {
                if(instance == null) instance = new InventoryManager();
            }
        }
        return instance;
    }

    public void registerProduct(Product product) {
        products.put(product.getId(), product);
    }

    public void registerWarehouse(Warehouse warehouse) {
        warehouses.put(warehouse.getId(), warehouse);
    }

    public boolean addStock(String productId, String warehouseId, int quantity) {
        if (quantity <= 0) {
            System.out.println("Invalid quantity " + quantity);
            return false;
        }
        if(!products.containsKey(productId)) {
            System.out.println("Invalid productId " + productId);
            return false;
        }
        if(!warehouses.containsKey(warehouseId)) {
            System.out.println("Invalid warehouseId " + warehouseId);
            return false;
        }

        String inventoryKey = productId + ":" + warehouseId;
        Inventory inventory = inventoryMap.get(inventoryKey);

        if(inventory == null) {

            System.out.println("Failed to add stock for " + productId + " in " + warehouseId + ".");
            return false;
        }

        try {
            if (inventory.acquireLock(1, TimeUnit.SECONDS)) {
                try {
                    inventory.setQuantity(inventory.getQuantity() + quantity);
                    historyManager.logTransaction(new Transaction(productId, warehouseId, quantity, TransactionType.ADD));
                    System.out.println("Added " + quantity + " units of " + products.get(productId).getName() + " to " + warehouses.get(warehouseId).getName() + ".");
                    return true;
                } finally {
                    inventory.releaseLock();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Failed to add stock for " + productId + " in " + warehouseId + ".");
        return false;
    }

    public boolean removeStock(String productId, String warehouseId, int quantity) {
        if (quantity <= 0) {
            System.out.println("Invalid quantity " + quantity);
            return false;
        }
        if(!products.containsKey(productId)) {
            System.out.println("Invalid productId " + productId);
            return false;
        }
        if(!warehouses.containsKey(warehouseId)) {
            System.out.println("Invalid warehouseId " + warehouseId);
            return false;
        }

        String inventoryKey = productId + ":" + warehouseId;
        Inventory inventory = inventoryMap.get(inventoryKey);

        if(inventory == null) {
            System.out.println("Invalid inventory.");
            return false;
        }

        try {
            if(inventory.acquireLock(1, TimeUnit.SECONDS)) {
                try {
                    if(inventory.getQuantity() < quantity) {
                        System.out.println("Sufficient quantity not available for product " + productId + " in warehouse " + warehouseId);
                        return false;
                    }
                    inventory.setQuantity(inventory.getQuantity() - quantity);
                    historyManager.logTransaction(new Transaction(productId, warehouseId, quantity, TransactionType.REMOVE));
                    System.out.println("Removed " + quantity + " units of " + products.get(productId).getName() + " from " + warehouses.get(warehouseId).getName() + ".");
                    if (inventory.isBelowThreshold()) {
                        System.out.println("ALERT: " + products.get(productId).getName() + " in " + warehouses.get(warehouseId).getName() + " is below minimum stock threshold of " + inventory.getMinThreshold());
                    }
                    return true;
                } finally {
                    inventory.releaseLock();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Failed to remove stock for " + productId + " in " + warehouseId + ". Insufficient stock or item not found.");
        return false;
    }

    public int getStockLevel(String productId, String warehouseId) {
        String inventoryKey = productId + ":" + warehouseId;
        Inventory inventory = inventoryMap.get(inventoryKey);
        return (inventory != null) ? inventory.getQuantity() : 0;
    }

    public void setMinStockThreshold(String productId, String warehouseId, int threshold) {
        String inventoryKey = productId + ":" + warehouseId;
        Inventory inventory = inventoryMap.get(inventoryKey);

        if(inventory == null) {
            System.out.println("Invalid inventory.");
            return;
        }

        try {
            if(inventory.acquireLock(1, TimeUnit.SECONDS)) {
                try {
                    inventory.setMinThreshold(threshold);
                    System.out.println("Set minimum stock threshold for " + products.get(productId).getName() + " in " + warehouses.get(warehouseId).getName() + " to " + threshold + ".");
                } finally {
                    inventory.releaseLock();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void addInventory(String productId, String warehouseId, int initialStock, int minThreshold) {
        Product product = products.get(productId);
        if(product == null) {
            System.out.println("Invalid productId " + productId);
            return;
        }
        Warehouse warehouse = warehouses.get(warehouseId);
        if(warehouse == null) {
            System.out.println("Invalid warehouseId " + warehouseId);
            return;
        }

        String inventoryKey = productId + ":" + warehouseId;
        if(inventoryMap.containsKey(inventoryKey)) {
            System.out.println("Duplicate inventory creation request");
            return;
        }

        inventoryMap.put(inventoryKey, new Inventory(product, warehouse, initialStock, minThreshold));
        historyManager.logTransaction(new Transaction(productId, warehouseId, initialStock, TransactionType.ADD));
        System.out.println("Added inventory for product " + productId + " in warehouse " + warehouseId);
    }

    public Inventory getInventory(String productId, String warehouseId) {
        String inventoryKey = productId + ":" + warehouseId;
        Inventory inventory = inventoryMap.get(inventoryKey);

        if(inventory == null) {
            System.out.println("Invalid inventory.");
        }

        return inventory;
    }

    public TransactionHistoryManager getHistoryManager() {
        return historyManager;
    }

    public void printStockLevels() {
        System.out.println("\n--- Current Stock Levels ---");
        for (Inventory inv : inventoryMap.values()) {
            System.out.println("Product: " + inv.getProduct().getName() +
                    " | Warehouse: " + inv.getWarehouse().getName() +
                    " | Stock: " + inv.getQuantity());
        }
        System.out.println("----------------------------\n");
    }

    public void printTransactionHistory() {
        System.out.println("\n--- Transaction History ---");
        List<Transaction> history = historyManager.getTransactionHistory();
        if (history.isEmpty()) {
            System.out.println("No transactions recorded.");
        } else {
            for (Transaction transaction : history) {
                System.out.println(transaction);
            }
        }
        System.out.println("---------------------------\n");
    }
}
