package com.workshop.refactored.repository;

import com.workshop.refactored.model.Product;

import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(int id);
    void updateStock(int productId, int newStock);
}
