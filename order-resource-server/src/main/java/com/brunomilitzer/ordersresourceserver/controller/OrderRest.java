package com.brunomilitzer.ordersresourceserver.controller;

public class OrderRest {
    private String orderId;
    private String productId;
    private String userId;
    private int quantity;
    private OrderStatus status;

    public OrderRest( String orderId, String productId, String userId, int quantity, OrderStatus status ) {
        this.orderId = orderId;
        this.productId = productId;
        this.userId = userId;
        this.quantity = quantity;
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getProductId() {
        return productId;
    }

    public String getUserId() {
        return userId;
    }

    public int getQuantity() {
        return quantity;
    }

    public OrderStatus getStatus() {
        return status;
    }

}
