package com.workshop.refactored.service;

public interface AuditService {
    void log(String action, String message);
}
