package com.workshop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {
    private StringUtils stringUtils;

    @BeforeEach
    void setUp() {
        stringUtils = new StringUtils();
    }

    @ParameterizedTest
    @ValueSource(strings = {"racecar", "A man a plan a canal Panama", "12321"})
    void testIsPalindrome_ValidPalindromes(String input) {
        assertTrue(stringUtils.isPalindrome(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"race a car", "hello", "world", "12345"})
    void testIsPalindrome_InvalidPalindromes(String input) {
        assertFalse(stringUtils.isPalindrome(input));
    }

    @Test
    void testIsPalindrome_NullInput() {
        assertFalse(stringUtils.isPalindrome(null));
    }

    @ParameterizedTest
    @CsvSource({
            "'hello world', 2",
            "'one', 1",
            "'  multiple   spaces  ', 2",
            "'', 0"
    })
    void testWordCount(String input, int expected) {
        assertEquals(expected, stringUtils.wordCount(input));
    }

    @Test
    void testWordCount_NullInput() {
        assertEquals(0, stringUtils.wordCount(null));
    }

    @ParameterizedTest
    @CsvSource({
            "'hello world', 'Hello World'",
            "'HELLO WORLD', 'Hello World'",
            "'hELLo WoRLd', 'Hello World'",
            "'single', 'Single'",
            "'multiple word test', 'Multiple Word Test'",
            "'', ''",
            "'a', 'A'"
    })
    void testCapitalize(String input, String expected) {
        assertEquals(expected, stringUtils.capitalize(input));
    }

    @Test
    void testCapitalize_NullInput() {
        assertNull(stringUtils.capitalize(null));
    }
    @ParameterizedTest
    @CsvSource({
            "'hello', 'olleh'",
            "'world', 'dlrow'",
            "'racecar', 'racecar'",
            "'12345', '54321'",
            "'', ''",
            "'a', 'a'"
    })
    void testReverse(String input, String expected) {
        assertEquals(expected, stringUtils.reverse(input));
    }

    @Test
    void testReverse_NullInput() {
        assertNull(stringUtils.reverse(null));
    }

    @ParameterizedTest
    @CsvSource({
            "'listen', 'silent', true",
            "'anagram', 'nagaram', true",
            "'rat', 'tar', true",
            "'hello', 'bello', false",
            "'world', 'word', false",
            "'abc', 'def', false",
            "'', '', true",
            "'a', 'a', true",
            "'ab', 'ba', true"
    })
    void testIsAnagram(String str1, String str2, boolean expected) {
        assertEquals(expected, stringUtils.isAnagram(str1, str2));
    }

    @Test
    void testIsAnagram_NullInputs() {
        assertFalse(stringUtils.isAnagram(null, "test"));
        assertFalse(stringUtils.isAnagram("test", null));
        assertFalse(stringUtils.isAnagram(null, null));
    }

    @ParameterizedTest
    @CsvSource({
            "'hello world', 'hll wrld'",
            "'Programming', 'Prgrmmng'",
            "'AEIOU', ''",
            "'bcdfg', 'bcdfg'",
            "'Education', 'dctn'",
            "'', ''"
    })
    void testRemoveVowels(String input, String expected) {
        assertEquals(expected, stringUtils.removeVowels(input));
    }

    @Test
    void testRemoveVowels_NullInput() {
        assertNull(stringUtils.removeVowels(null), "Remove vowels with null input should return null");
    }

    @ParameterizedTest
    @CsvSource({
            "'hello', 'l', 2",
            "'programming', 'm', 2",
            "'test', 't', 2",
            "'hello world', 'o', 2",
            "'abcdef', 'x', 0",
            "'', 'a', 0",
            "'aaaaaa', 'a', 6",
            "'Hello', 'H', 1",
            "'Hello', 'h', 0"
    })
    void testCountOccurrences(String str, char ch, int expected) {
        assertEquals(expected, stringUtils.countOccurrences(str, ch));
    }

    @Test
    void testCountOccurrences_NullInput() {
        assertEquals(0, stringUtils.countOccurrences(null, 'a'));
    }
}
