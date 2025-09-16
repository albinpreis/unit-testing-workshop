package com.workshop.legacy;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * LEGACY CODE WARNING: This is intentionally bad code!
 * <p>
 * This class represents typical legacy code issues:<p>
 * - Tight coupling to database<p>
 * - Hard to test dependencies (static calls, new operators)<p>
 * - Mixed responsibilities<p>
 * - No dependency injection<p>
 * - Hard-coded values<p>
 * - Complex methods doing too many things
 */

public class OrderProcessor {

    // Hard-coded database connection - makes testing difficult
    private static final String DB_URL = "jdbc:mysql://localhost:3306/orders";
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "password123";

    public String processOrder(int customerId, List<OrderItem> items) {
        // This method does way too many things!

        if (items == null || items.isEmpty()) {
            return "ERROR: No items to process";
        }

        try {
            // Direct database access - hard to mock
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Check customer exists
            PreparedStatement customerStmt = conn.prepareStatement(
                    "SELECT * FROM customers WHERE id = ?");
            customerStmt.setInt(1, customerId);
            ResultSet customerResult = customerStmt.executeQuery();

            if (!customerResult.next()) {
                conn.close();
                return "ERROR: Customer not found";
            }

            String customerName = customerResult.getString("name");
            String customerEmail = customerResult.getString("email");
            boolean isPremium = customerResult.getBoolean("is_premium");

            // Calculate total with complex business logic embedded
            BigDecimal total = BigDecimal.ZERO;
            for (OrderItem item : items) {
                // Direct database call for each item - inefficient and untestable
                PreparedStatement itemStmt = conn.prepareStatement(
                        "SELECT price, stock_quantity FROM products WHERE id = ?");
                itemStmt.setInt(1, item.getProductId());
                ResultSet itemResult = itemStmt.executeQuery();

                if (!itemResult.next()) {
                    conn.close();
                    return "ERROR: Product " + item.getProductId() + " not found";
                }

                BigDecimal price = itemResult.getBigDecimal("price");
                int stock = itemResult.getInt("stock_quantity");

                if (stock < item.getQuantity()) {
                    conn.close();
                    return "ERROR: Insufficient stock for product " + item.getProductId();
                }

                BigDecimal itemTotal = price.multiply(BigDecimal.valueOf(item.getQuantity()));
                total = total.add(itemTotal);

                // Update stock - more database calls
                PreparedStatement updateStmt = conn.prepareStatement(
                        "UPDATE products SET stock_quantity = ? WHERE id = ?");
                updateStmt.setInt(1, stock - item.getQuantity());
                updateStmt.setInt(2, item.getProductId());
                updateStmt.executeUpdate();
            }

            // Apply discount logic - hardcoded business rules
            if (isPremium) {
                if (total.compareTo(new BigDecimal("100.00")) >= 0) {
                    total = total.multiply(new BigDecimal("0.85")); // 15% discount
                } else {
                    total = total.multiply(new BigDecimal("0.90")); // 10% discount
                }
            } else {
                if (total.compareTo(new BigDecimal("200.00")) >= 0) {
                    total = total.multiply(new BigDecimal("0.95")); // 5% discount
                }
            }

            // Create order record
            PreparedStatement orderStmt = conn.prepareStatement(
                    "INSERT INTO orders (customer_id, total_amount, order_date, status) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            orderStmt.setInt(1, customerId);
            orderStmt.setBigDecimal(2, total);
            orderStmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            orderStmt.setString(4, "PENDING");

            int result = orderStmt.executeUpdate();
            if (result == 0) {
                conn.close();
                return "ERROR: Failed to create order";
            }

            // Get generated order ID
            ResultSet generatedKeys = orderStmt.getGeneratedKeys();
            int orderId = 0;
            if (generatedKeys.next()) {
                orderId = generatedKeys.getInt(1);
            }

            // Send email notification - another external dependency
            EmailSender.sendOrderConfirmation(customerEmail, customerName, orderId, total);

            // Log to external service - yet another dependency
            AuditLogger.log("ORDER_CREATED", "Order " + orderId + " created for customer " + customerId);

            conn.close();

            return "SUCCESS: Order " + orderId + " processed. Total: $" + total;

        } catch (SQLException e) {
            return "ERROR: Database error - " + e.getMessage();
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }

    // Another problematic method with static dependencies
    public boolean cancelOrder(int orderId) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Check if order exists and is cancelable
            PreparedStatement checkStmt = conn.prepareStatement(
                    "SELECT status, customer_id FROM orders WHERE id = ?");
            checkStmt.setInt(1, orderId);
            ResultSet result = checkStmt.executeQuery();

            if (!result.next()) {
                conn.close();
                return false;
            }

            String status = result.getString("status");
            int customerId = result.getInt("customer_id");

            if ("SHIPPED".equals(status) || "DELIVERED".equals(status)) {
                conn.close();
                return false; // Can't cancel shipped/delivered orders
            }

            // Update order status
            PreparedStatement updateStmt = conn.prepareStatement(
                    "UPDATE orders SET status = 'CANCELLED' WHERE id = ?");
            updateStmt.setInt(1, orderId);
            int updated = updateStmt.executeUpdate();

            if (updated > 0) {
                // Send cancellation email
                PreparedStatement customerStmt = conn.prepareStatement(
                        "SELECT email, name FROM customers WHERE id = ?");
                customerStmt.setInt(1, customerId);
                ResultSet customerResult = customerStmt.executeQuery();

                if (customerResult.next()) {
                    String email = customerResult.getString("email");
                    String name = customerResult.getString("name");
                    EmailSender.sendCancellationConfirmation(email, name, orderId);
                }

                AuditLogger.log("ORDER_CANCELLED", "Order " + orderId + " cancelled");
            }

            conn.close();
            return updated > 0;

        } catch (Exception e) {
            System.err.println("Error cancelling order: " + e.getMessage());
            return false;
        }
    }
}
