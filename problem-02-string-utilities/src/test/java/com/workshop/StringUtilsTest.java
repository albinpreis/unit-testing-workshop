package com.workshop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class StringUtilsTest {
    private StringUtils stringUtils;

    @BeforeEach
    void setUp() {
        stringUtils = new StringUtils();
    }

    // TODO: Write parameterized test for isPalindrome with multiple valid palindromes
    @ParameterizedTest
    @ValueSource(strings = {"racecar", "A man a plan a canal Panama", "race a car"})
    void testIsPalindrome_ValidPalindromes(String input) {
        // Your test here
    }

    // TODO: Write parameterized test for isPalindrome with invalid cases

    // TODO: Write test for isPalindrome with null input

    // TODO: Write parameterized test for wordCount with different inputs
    @ParameterizedTest
    @CsvSource({
            "'hello world', 2",
            "'one', 1",
            "'  multiple   spaces  ', 2",
            "'', 0"
    })
    void testWordCount(String input, int expected) {
        // Your test here
    }

    // TODO: Write test for wordCount with null input

    // TODO: Write parameterized test for capitalize function

    // TODO: Write tests for reverse function (including null case)

    // TODO: Write parameterized test for isAnagram

    // TODO: Write tests for removeVowels

    // TODO: Write parameterized test for countOccurrences
}
