package com.workshop.legacy;

public class OrderItem {
    private int productId;
    private int quantity;

    public OrderItem(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getProductId() { return productId; }
    public int getQuantity() { return quantity; }
}
