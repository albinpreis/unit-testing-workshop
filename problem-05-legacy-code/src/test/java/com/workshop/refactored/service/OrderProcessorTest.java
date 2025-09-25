package com.workshop.refactored.service;

import com.workshop.legacy.OrderItem;
import com.workshop.refactored.model.Customer;
import com.workshop.refactored.model.Order;
import com.workshop.refactored.model.Product;
import com.workshop.refactored.repository.CustomerRepository;
import com.workshop.refactored.repository.OrderRepository;
import com.workshop.refactored.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderProcessorTest {
    @Mock
    private CustomerRepository customerRepo;
    @Mock
    private ProductRepository productRepo;
    @Mock
    private OrderRepository orderRepo;
    @Mock
    private EmailService emailService;
    @Mock
    private AuditService auditService;

    @InjectMocks
    private OrderProcessor orderProcessor;


    @Test
    void testProcessOrder_Success() {
        Customer customer = new Customer(1, "John", "john@example.com", true);
        Product product = new Product(1, "Widget", new BigDecimal("10.00"), 100);
        Order savedOrder = new Order(123, 1, new BigDecimal("20.00"));

        when(customerRepo.findById(1)).thenReturn(Optional.of(customer));
        when(productRepo.findById(1)).thenReturn(Optional.of(product));
        when(orderRepo.save(any())).thenReturn(savedOrder);

        String result = orderProcessor.processOrder(1, List.of(new OrderItem(1, 2)));

        assertTrue(result.startsWith("SUCCESS"));
        verify(emailService).sendOrderConfirmation(
                eq("john@example.com"),
                eq("John"),
                eq(123),
                argThat(amount -> amount.compareTo(new BigDecimal("18.00")) == 0)
        );
        verify(auditService).log(eq("ORDER_CREATED"), contains("Order 123"));
    }
}
