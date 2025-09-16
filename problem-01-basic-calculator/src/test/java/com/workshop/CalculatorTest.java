package com.workshop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Calculator Tests")
class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    void testAddition() {
        // Arrange
        int a = 5;
        int b = 3;
        int expected = 8;

        // Act
        int result = calculator.add(a, b);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void testSubtractionWithNegativeResult() {
        int a = 3;
        int b = 8;
        int expected = -5;

        int result = calculator.subtract(a, b);

        assertEquals(expected, result);
    }

    @Test
    void testMultiplicationWithZero() {
        int a = 7;
        int b = 0;
        int expected = 0;

        int result = calculator.multiply(a, b);

        assertEquals(expected, result);
    }

    @Test
    void testDivisionNormalCase() {
        int a = 10;
        int b = 2;
        double expected = 5.0;

        double result = calculator.divide(a, b);

        assertEquals(expected, result);
    }

    @Test
    void testDivisionByZero() {
        int a = 10;
        int b = 0;

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> calculator.divide(a, b)
        );
        assertEquals("Division by zero is not allowed", exception.getMessage());
    }

    @Test
    void testPowerFunction() {
        int base = 2;
        int exponent = 3;
        int expected = 8;

        int result = calculator.power(base, exponent);

        assertEquals(expected, result);
    }

    @Test
    void testPowerWithZeroExponent() {
        int base = 5;
        int exponent = 0;
        int expected = 1;

        int result = calculator.power(base, exponent);

        assertEquals(expected, result);
    }

    @Test
    void testPowerWithNegativeExponent() {
        int base = 2;
        int exponent = -1;

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> calculator.power(base, exponent)
        );
        assertEquals("Negative exponents not supported", exception.getMessage());
    }

    @Test
    void testIsEvenWithEvenNumber() {
        int evenNumber = 4;

        boolean result = calculator.isEven(evenNumber);

        assertTrue(result);
    }

    @Test
    void testIsEvenWithOddNumber() {
        int oddNumber = 7;

        boolean result = calculator.isEven(oddNumber);

        assertFalse(result);
    }

    @Test
    void testIsEvenWithZero() {
        int zero = 0;

        boolean result = calculator.isEven(zero);

        assertTrue(result, "Zero should be considered even");
    }

    @Test
    void testIsEvenWithNegativeNumbers() {
        assertTrue(calculator.isEven(-4), "Negative even number should return true");
        assertFalse(calculator.isEven(-3), "Negative odd number should return false");
    }
}