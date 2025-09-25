package com.workshop.refactored.service;

import com.workshop.legacy.OrderItem;
import com.workshop.refactored.model.Customer;
import com.workshop.refactored.model.Order;
import com.workshop.refactored.model.Product;
import com.workshop.refactored.repository.CustomerRepository;
import com.workshop.refactored.repository.OrderRepository;
import com.workshop.refactored.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

public class OrderProcessor {
    private final CustomerRepository customerRepo;
    private final ProductRepository productRepo;
    private final OrderRepository orderRepo;
    private final EmailService emailService;
    private final AuditService auditService;

    public OrderProcessor(CustomerRepository customerRepo,
                          ProductRepository productRepo,
                          OrderRepository orderRepo,
                          EmailService emailService,
                          AuditService auditService) {
        this.customerRepo = customerRepo;
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
        this.emailService = emailService;
        this.auditService = auditService;
    }

    public String processOrder(int customerId, List<OrderItem> items) {
        if (items == null || items.isEmpty()) {
            return "ERROR: No items to process";
        }

        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem item : items) {
            Product product = productRepo.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock for " + product.getId());
            }

            productRepo.updateStock(product.getId(), product.getStock() - item.getQuantity());
            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        // Apply discounts
        if (customer.isPremium()) {
            if (total.compareTo(new BigDecimal("100.00")) >= 0) {
                total = total.multiply(new BigDecimal("0.85"));
            } else {
                total = total.multiply(new BigDecimal("0.90"));
            }
        } else {
            if (total.compareTo(new BigDecimal("200.00")) >= 0) {
                total = total.multiply(new BigDecimal("0.95"));
            }
        }

        Order savedOrder = orderRepo.save(new Order(0, customer.getId(), total));

        emailService.sendOrderConfirmation(customer.getEmail(), customer.getName(), savedOrder.getId(), total);
        auditService.log("ORDER_CREATED", "Order " + savedOrder.getId() + " created for customer " + customerId);

        return "SUCCESS: Order " + savedOrder.getId() + " processed. Total: $" + total;
    }
}
