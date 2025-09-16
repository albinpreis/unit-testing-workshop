package com.workshop;

import java.util.Arrays;

/**
 * String utility class with various string manipulation methods.
 * <p>
 * Tests should be written for:<p>
 * - Parameterized tests for multiple inputs<p>
 * - Null and empty string handling<p>
 * - Edge cases and boundary conditions<p>
 * - Case sensitivity scenarios
 */
public class StringUtils {

    public boolean isPalindrome(String str) {
        if (str == null) {
            return false;
        }

        String cleaned = str.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
        String reversed = new StringBuilder(cleaned).reverse().toString();
        return cleaned.equals(reversed);
    }

    public int wordCount(String str) {
        if (str == null || str.trim().isEmpty()) {
            return 0;
        }

        return str.trim().split("\\s+").length;
    }

    public String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        return Arrays.stream(str.split("\\s+"))
                .map(word -> word.substring(0, 1).toUpperCase() +
                        word.substring(1).toLowerCase())
                .reduce((a, b) -> a + " " + b)
                .orElse("");
    }

    public String reverse(String str) {
        if (str == null) {
            return null;
        }

        return new StringBuilder(str).reverse().toString();
    }

    public boolean isAnagram(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return false;
        }

        if (str1.length() != str2.length()) {
            return false;
        }

        char[] chars1 = str1.toLowerCase().toCharArray();
        char[] chars2 = str2.toLowerCase().toCharArray();

        Arrays.sort(chars1);
        Arrays.sort(chars2);

        return Arrays.equals(chars1, chars2);
    }

    public String removeVowels(String str) {
        if (str == null) {
            return null;
        }

        return str.replaceAll("[aeiouAEIOU]", "");
    }

    public int countOccurrences(String str, char ch) {
        if (str == null) {
            return 0;
        }

        return (int) str.chars()
                .filter(c -> c == ch)
                .count();
    }
}

