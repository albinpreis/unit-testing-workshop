package com.workshop.legacy;

import java.time.LocalDateTime;

/**
 * Another static dependency that's hard to test
 */
public class AuditLogger {
    public static void log(String action, String message) {
        // Simulates logging to external service
        System.out.println("AUDIT: " + LocalDateTime.now() + " - " + action + ": " + message);

        // Simulate potential failure
        if (message.contains("ERROR_TEST")) {
            throw new RuntimeException("Audit service unavailable");
        }
    }
}
