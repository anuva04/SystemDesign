package org.example;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Inventory {
    private Product product;
    private Warehouse warehouse;
    private int quantity;
    private int minThreshold;
    private final ReentrantLock lock = new ReentrantLock();
    private final Map<String, Integer> reservedStock = new ConcurrentHashMap<>();

    public Inventory(Product product, Warehouse warehouse, int quantity, int minThreshold) {
        this.product = product;
        this.warehouse = warehouse;
        this.quantity = quantity;
        this.minThreshold = minThreshold;
    }

    public boolean acquireLock(long timeout, TimeUnit unit) throws InterruptedException {
        return lock.tryLock(timeout, unit);
    }

    public void releaseLock() {
        if(lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getMinThreshold() {
        return minThreshold;
    }

    public void setMinThreshold(int threshold) {
        this.minThreshold = threshold;
    }

    public boolean isBelowThreshold() {
        return quantity < minThreshold;
    }

    public Product getProduct() { return product; }
    public Warehouse getWarehouse() { return warehouse; }

    public void reserveStock(String orderId, int quantity) {
        reservedStock.merge(orderId, quantity, Integer::sum);
    }

    public void releaseReservedStock(String orderId) {
        reservedStock.remove(orderId);
    }

    public Map<String, Integer> getReservedStock() {
        return reservedStock;
    }
}
