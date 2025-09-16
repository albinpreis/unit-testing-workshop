package com.workshop;

public interface EmailService {
    void sendWelcomeEmail(String email, String username);
    void sendPasswordResetEmail(String email, String resetToken);
    boolean isValidEmailFormat(String email);
}
