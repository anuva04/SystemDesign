package org.example;

public class OrderItem {
    private Product product;
    private Warehouse warehouse;
    private int quantity;

    public OrderItem(Product product, Warehouse warehouse, int quantity) {
        this.product = product;
        this.warehouse = warehouse;
        this.quantity = quantity;
    }

    public Product getProduct() { return product; }
    public Warehouse getWarehouse() { return warehouse; }
    public int getQuantity() { return quantity; }
}
