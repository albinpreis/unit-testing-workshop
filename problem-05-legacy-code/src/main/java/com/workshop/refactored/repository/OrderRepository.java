package com.workshop.refactored.repository;

import com.workshop.refactored.model.Order;

public interface OrderRepository {
    Order save(Order order);
}
