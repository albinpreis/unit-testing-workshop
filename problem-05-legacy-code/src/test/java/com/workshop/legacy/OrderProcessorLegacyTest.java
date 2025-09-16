package com.workshop.legacy;

import org.testng.annotations.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Legacy Code Testing Workshop
 * <p>
 * Challenge: Test this legacy code without modifying the production code initially.
 * <p>
 * Techniques to learn:<p>
 * 1. Characterization tests - document existing behavior<p>
 * 2. Identify testing seams<p>
 * 3. Extract and Override method<p>
 * 4. Dependency injection<p>
 * 5. Wrap external dependencies<p>
 * <p>
 * Phase 1: Write characterization tests (document current behavior)<p>
 * Phase 2: Identify refactoring opportunities<p>
 * Phase 3: Safely refactor with test coverage<p>
 */
public class OrderProcessorLegacyTest {

    private OrderProcessor orderProcessor;

    @BeforeEach
    void setUp() {
        orderProcessor = new OrderProcessor();
    }

    // Phase 1: Characterization Tests
    // These tests document the current behavior, even if it's not ideal

    @Test
    void testProcessOrder_EmptyItems_ReturnsError() {
        // This test can run without database - good starting point
        String result = orderProcessor.processOrder(1, Collections.emptyList());
        assertEquals("ERROR: No items to process", result);
    }

    @Test
    void testProcessOrder_NullItems_ReturnsError() {
        String result = orderProcessor.processOrder(1, null);
        assertEquals("ERROR: No items to process", result);
    }

    // TODO: These tests will fail because they need a real database
    // How do we test this?

    @Disabled("Requires database connection - how can we test this?")
    @Test
    void testProcessOrder_InvalidCustomer_ReturnsError() {
        OrderItem item = new OrderItem(1, 2);
        String result = orderProcessor.processOrder(999, List.of(item));
        assertTrue(result.startsWith("ERROR: Customer not found"));
    }

    @Disabled("Requires database connection")
    @Test
    void testProcessOrder_ValidOrder_ReturnsSuccess() {
        // How do we test this without a database?
        // What data needs to be in the database?
        // How do we control the database state?
        fail("You need to figure out how to test this");
    }

    @Disabled("Requires database connection")
    @Test
    void testProcessOrder_InsufficientStock_ReturnsError() {
        // How do we test stock validation?
        fail("You need to figure out how to test this");
    }

    @Disabled("How do we test email sending?")
    @Test
    void testProcessOrder_EmailFailure_StillCreatesOrder() {
        // What happens if email fails? Should order still be created?
        // How do we test this behavior?
        fail("You need to figure out how to test email scenarios");
    }

    // Phase 2: Refactoring Challenges
    // You should identify these issues and solutions:

    /*
     * PROBLEMS IDENTIFIED:
     * 1. Database coupling - hard to test, slow tests
     * 2. Static dependencies (EmailSender, AuditLogger) - can't mock
     * 3. Mixed responsibilities - business logic + data access + side effects
     * 4. Hard-coded configuration
     * 5. Complex method doing too many things
     * 6. No error handling strategy
     *
     * SOLUTIONS TO EXPLORE:
     * 1. Extract database operations to repository pattern
     * 2. Wrap static calls in testable interfaces
     * 3. Dependency injection
     * 4. Extract business logic to separate service
     * 5. Break large methods into smaller, focused ones
     * 6. Better error handling with custom exceptions
     */

    // Phase 3: After refactoring, tests should look like this:

    /*
    @Test
    void testProcessOrder_Success() {
        // Given
        Customer customer = new Customer(1, "John", "john@example.com", true);
        Product product = new Product(1, "Widget", new BigDecimal("10.00"), 100);
        OrderItem item = new OrderItem(1, 2);

        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(orderRepository.save(any())).thenReturn(savedOrder);

        // When
        String result = orderProcessor.processOrder(1, Arrays.asList(item));

        // Then
        assertTrue(result.startsWith("SUCCESS"));
        verify(emailService).sendOrderConfirmation(any());
        verify(auditService).log(any(), any());
    }
    */
}
