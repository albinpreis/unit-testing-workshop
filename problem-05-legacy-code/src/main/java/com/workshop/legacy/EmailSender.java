package com.workshop.legacy;

import java.math.BigDecimal;

/**
 * Static utility class - hard to mock in tests
 */
public class EmailSender {
    public static void sendOrderConfirmation(String email, String customerName, int orderId, BigDecimal total) {
        // Simulates sending email - in real code this might call external service
        System.out.println("EMAIL: Order confirmation sent to " + email);

        // Simulate potential failure
        if ("fail@test.com".equals(email)) {
            throw new RuntimeException("Email service unavailable");
        }
    }

    public static void sendCancellationConfirmation(String email, String customerName, int orderId) {
        System.out.println("EMAIL: Order cancellation confirmation sent to " + email);

        if ("fail@test.com".equals(email)) {
            throw new RuntimeException("Email service unavailable");
        }
    }
}
