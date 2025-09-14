package org.example;

import java.util.List;

public class Order {
    private String orderId;
    private List<OrderItem> orderItems;
    private OrderStatus status;

    public Order(String orderId, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.orderItems = orderItems;
        this.status = OrderStatus.PENDING;
    }

    public String getOrderId() { return orderId; }
    public List<OrderItem> getOrderItems() { return orderItems; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
}
