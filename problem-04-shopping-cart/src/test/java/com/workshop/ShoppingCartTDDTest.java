package com.workshop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

/**
 * TDD Workshop: Shopping Cart
 * <p>
 * Instructions:<p>
 * 1. Read each test method below<p>
 * 2. Run the test - it should FAIL (Red)<p>
 * 3. Write the minimal code to make it pass (Green)<p>
 * 4. Refactor if needed while keeping tests green<p>
 * 5. Move to the next test<p>
 * Follow this order strictly - don't skip ahead!
 */

public class ShoppingCartTDDTest {

    private ShoppingCart cart;
    private Product apple;
    private Product banana;

    @BeforeEach
    void setUp() {
        cart = new ShoppingCart(); // This will fail initially - you need to create the class!
        apple = new Product("APPLE", "Apple", new BigDecimal("1.50"));
        banana = new Product("BANANA", "Banana", new BigDecimal("0.75"));
    }

    // Test 1: Start here - create an empty cart
    @Test
    void newCartShouldBeEmpty() {
        assertTrue(cart.isEmpty());
        assertEquals(0, cart.getItemCount());
        assertEquals(new BigDecimal("0.00"), cart.getSubtotal());
    }

    // Test 2: Add a single item to cart
    @Test
    void shouldAddSingleItemToCart() {
        cart.addItem(apple, 2);

        assertFalse(cart.isEmpty());
        assertEquals(2, cart.getItemCount());
        assertEquals(new BigDecimal("3.00"), cart.getSubtotal()); // 2 * 1.50
    }

    // Test 3: Add multiple different items
    @Test
    void shouldAddMultipleDifferentItems() {
        cart.addItem(apple, 2);
        cart.addItem(banana, 3);

        assertEquals(5, cart.getItemCount()); // 2 + 3
        assertEquals(new BigDecimal("5.25"), cart.getSubtotal()); // 3.00 + 2.25
    }

    // Test 4: Adding same item should update quantity
    @Test
    void shouldUpdateQuantityWhenAddingSameItem() {
        cart.addItem(apple, 2);
        cart.addItem(apple, 3); // Should now have 5 apples total

        assertEquals(5, cart.getItemCount());
        assertEquals(new BigDecimal("7.50"), cart.getSubtotal()); // 5 * 1.50
    }

    // Test 5: Remove item from cart
    @Test
    void shouldRemoveItemFromCart() {
        cart.addItem(apple, 2);
        cart.addItem(banana, 3);

        cart.removeItem("APPLE");

        assertEquals(3, cart.getItemCount());
        assertEquals(new BigDecimal("2.25"), cart.getSubtotal()); // Only bananas left
    }

    // Test 6: Update item quantity
    @Test
    void shouldUpdateItemQuantity() {
        cart.addItem(apple, 2);

        cart.updateQuantity("APPLE", 5);

        assertEquals(5, cart.getItemCount());
        assertEquals(new BigDecimal("7.50"), cart.getSubtotal());
    }

    // Test 7: Clear cart
    @Test
    void shouldClearAllItems() {
        cart.addItem(apple, 2);
        cart.addItem(banana, 3);

        cart.clear();

        assertTrue(cart.isEmpty());
        assertEquals(0, cart.getItemCount());
        assertEquals(new BigDecimal("0.00"), cart.getSubtotal());
    }

    // Test 8: Apply discount
    @Test
    void shouldApplyDiscount() {
        cart.addItem(apple, 4); // $6.00 subtotal

        cart.applyDiscount(new BigDecimal("1.00"));

        assertEquals(new BigDecimal("6.00"), cart.getSubtotal()); // Subtotal unchanged
        assertEquals(new BigDecimal("5.00"), cart.getTotal()); // Total after discount
    }

    // Test 9: Handle edge cases
    @Test
    void shouldHandleInvalidQuantity() {
        assertThrows(IllegalArgumentException.class, () -> {
            cart.addItem(apple, -1);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            cart.addItem(apple, 0);
        });
    }

    @Test
    void shouldHandleNullProduct() {
        assertThrows(IllegalArgumentException.class, () -> {
            cart.addItem(null, 1);
        });
    }

    @Test
    void shouldHandleRemoveNonExistentItem() {
        // This should not throw an exception, just do nothing
        cart.removeItem("NONEXISTENT");
        assertTrue(cart.isEmpty());
    }

    // Test 10: Get cart items
    @Test
    void shouldReturnCartItems() {
        cart.addItem(apple, 2);
        cart.addItem(banana, 1);

        var items = cart.getItems();

        assertEquals(2, items.size());
        // Verify the items contain correct products and quantities
        // You will need to implement CartItem class for this
    }
}
