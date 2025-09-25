package com.workshop.refactored.model;

import java.math.BigDecimal;

public class Order {
    private final int id;
    private final int customerId;
    private final BigDecimal total;

    public Order(int id, int customerId, BigDecimal total) {
        this.id = id;
        this.customerId = customerId;
        this.total = total;
    }

    public int getId() { return id; }
    public int getCustomerId() { return customerId; }
    public BigDecimal getTotal() { return total; }
}
