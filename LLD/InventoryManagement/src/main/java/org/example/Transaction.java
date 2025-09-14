package org.example;

import java.util.Date;
import java.util.UUID;

public class Transaction {
    private String transactionId;
    private String productId;
    private String warehouseId;
    private int quantity;
    private Date timestamp;
    private TransactionType type;

    public Transaction(String productId, String warehouseId, int quantity, TransactionType type) {
        this.transactionId = UUID.randomUUID().toString();
        this.productId = productId;
        this.warehouseId = warehouseId;
        this.quantity = quantity;
        this.timestamp = new Date();
        this.type = type;
    }

    @Override
    public String toString() {
        return "Transaction ID: " + transactionId +
                " | Product ID: " + productId +
                " | Warehouse ID: " + warehouseId +
                " | Change: " + quantity +
                " | Type: " + type +
                " | Timestamp: " + timestamp;
    }
}
