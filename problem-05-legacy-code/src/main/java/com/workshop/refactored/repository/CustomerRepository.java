package com.workshop.refactored.repository;

import com.workshop.refactored.model.Customer;

import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> findById(int id);
}
