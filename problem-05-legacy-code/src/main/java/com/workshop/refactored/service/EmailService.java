package com.workshop.refactored.service;

import java.math.BigDecimal;

public interface EmailService {
    void sendOrderConfirmation(String email, String name, int orderId, BigDecimal total);
    void sendCancellationConfirmation(String email, String name, int orderId);
}
