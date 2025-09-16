package com.workshop;

/**
 * A simple calculator class for basic arithmetic operations.
 * <p>
 * Tests should be written for:<p>
 * - Basic operations (happy path)<p>
 * - Edge cases (zero, negative numbers)<p>
 * - Error conditions (division by zero)<p>
 * - Boundary values
 */
public class Calculator {

    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public double divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Division by zero is not allowed");
        }
        return (double) a / b;
    }

    public int power(int base, int exponent) {
        if (exponent < 0) {
            throw new IllegalArgumentException("Negative exponents not supported");
        }

        int result = 1;
        for (int i = 0; i < exponent; i++) {
            result *= base;
        }
        return result;
    }

    public boolean isEven(int number) {
        return number % 2 == 0;
    }
}