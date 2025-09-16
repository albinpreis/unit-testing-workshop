package com.workshop;

import java.math.BigDecimal;
import java.util.*;

public class ShoppingCart {
    private final Map<String, CartItem> items;
    private BigDecimal discountAmount;

    public ShoppingCart() {
        this.items = new HashMap<>();
        this.discountAmount = BigDecimal.ZERO;
    }

    public void addItem(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        String productId = product.getId();

        if (items.containsKey(productId)) {
            // Update existing item quantity
            CartItem existingItem = items.get(productId);
            existingItem.addQuantity(quantity);
        } else {
            // Add new item
            CartItem newItem = new CartItem(product, quantity);
            items.put(productId, newItem);
        }
    }

    public void removeItem(String productId) {
        if (productId != null) {
            items.remove(productId);
        }
    }

    public void updateQuantity(String productId, int quantity) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        CartItem item = items.get(productId);
        if (item != null) {
            item.setQuantity(quantity);
        }
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(items.values());
    }

    public int getItemCount() {
        return items.values().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    public BigDecimal getSubtotal() {
        return items.values().stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void applyDiscount(BigDecimal discountAmount) {
        if (discountAmount == null) {
            throw new IllegalArgumentException("Discount amount cannot be null");
        }
        if (discountAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Discount amount cannot be negative");
        }

        this.discountAmount = discountAmount;
    }

    public BigDecimal getTotal() {
        BigDecimal subtotal = getSubtotal();
        return subtotal.subtract(discountAmount).max(BigDecimal.ZERO);
    }

    public void clear() {
        items.clear();
        discountAmount = BigDecimal.ZERO;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public boolean containsProduct(String productId) {
        return items.containsKey(productId);
    }

    public Optional<CartItem> getItem(String productId) {
        return Optional.ofNullable(items.get(productId));
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "items=" + items.size() +
                ", totalItems=" + getItemCount() +
                ", subtotal=" + getSubtotal() +
                ", discount=" + discountAmount +
                ", total=" + getTotal() +
                '}';
    }
}
