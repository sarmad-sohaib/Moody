package com.sarmad.moody.core.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.Locale
import java.util.concurrent.TimeUnit

class UtilExtsTest {
    private val delta = 0.001 // For floating point comparisons

    @Test
    fun `toCelsius converts absolute zero correctly`() {
        val kelvin = 0.0
        val expectedCelsius = -273.15
        assertEquals(expectedCelsius, kelvin.toCelsius(), delta)
    }

    @Test
    fun `toCelsius converts freezing point of water correctly`() {
        val kelvin = 273.15
        val expectedCelsius = 0.0
        assertEquals(expectedCelsius, kelvin.toCelsius(), delta)
    }

    @Test
    fun `toCelsius converts boiling point of water correctly`() {
        val kelvin = 373.15
        val expectedCelsius = 100.0
        assertEquals(expectedCelsius, kelvin.toCelsius(), delta)
    }

    @Test
    fun `toCelsius converts common positive temperature correctly`() {
        val kelvin = 293.15 // 20 C
        val expectedCelsius = 20.0
        assertEquals(expectedCelsius, kelvin.toCelsius(), delta)
    }

    @Test
    fun `toCelsius converts common negative temperature correctly`() {
        val kelvin = 253.15 // -20 C
        val expectedCelsius = -20.0
        assertEquals(expectedCelsius, kelvin.toCelsius(), delta)
    }

    @Test
    fun `toCelsius rounds up correctly`() {
        val kelvin = 300.128 // 26.978 C
        val expectedCelsius = 26.98
        assertEquals(expectedCelsius, kelvin.toCelsius(), delta)
    }

    @Test
    fun `toCelsius rounds down correctly`() {
        val kelvin = 300.123 // 26.973 C
        val expectedCelsius = 26.97
        assertEquals(expectedCelsius, kelvin.toCelsius(), delta)
    }

    @Test
    fun `toCelsius handles multiple decimal places before rounding correctly`() {
        val kelvin = 274.16123 // 1.01123 C
        val expectedCelsius = 1.01
        assertEquals(expectedCelsius, kelvin.toCelsius(), delta)
    }

    @Test
    fun `toSentenceCase on empty string returns empty string`() {
        assertEquals("", "".toSentenceCase())
    }

    @Test
    fun `toSentenceCase on single lowercase char returns uppercase`() {
        assertEquals("A", "a".toSentenceCase())
    }

    @Test
    fun `toSentenceCase on single uppercase char returns uppercase`() {
        assertEquals("A", "A".toSentenceCase())
    }

    @Test
    fun `toSentenceCase on all lowercase string`() {
        assertEquals("Hello world", "hello world".toSentenceCase())
    }

    @Test
    fun `toSentenceCase on all uppercase string`() {
        assertEquals("Hello world", "HELLO WORLD".toSentenceCase())
    }

    @Test
    fun `toSentenceCase on mixed case string`() {
        assertEquals("Hello world", "hELLo wORLd".toSentenceCase())
    }

    @Test
    fun `toSentenceCase on already sentence case string`() {
        assertEquals("Hello world", "Hello world".toSentenceCase())
    }

    @Test
    fun `toSentenceCase with leading and trailing spaces`() {
        // toSentenceCase() itself doesn't trim, so spaces should be preserved.
        // The current implementation lowercases the whole string first.
        assertEquals("  hello world  ", "  Hello World  ".toSentenceCase())
        assertEquals("  hello world  ", "  hello world  ".toSentenceCase())
        assertEquals("  hello world  ", "  HELLO WORLD  ".toSentenceCase())
    }

    @Test
    fun `toSentenceCase with leading number`() {
        assertEquals("1hello world", "1hello world".toSentenceCase())
        assertEquals("1hello world", "1Hello World".toSentenceCase())
    }

    @Test
    fun `toSentenceCase with leading symbol`() {
        assertEquals("!hello world", "!hello world".toSentenceCase())
        assertEquals("!hello world", "!Hello World".toSentenceCase())
    }

    @Test
    fun `toSentenceCase with symbol then letter`() {
        assertEquals("!hello world", "!hello world".toSentenceCase()) // Stays lowercase after !
        assertEquals(".hello world", ".hello world".toSentenceCase()) // Stays lowercase after .
    }

    @Test
    fun `toSentenceCase with multiple sentences`() {
        assertEquals("Hello world. this is a test.", "hello world. this is a test.".toSentenceCase())
        assertEquals("Hello world. this is a test.", "Hello world. This is a test.".toSentenceCase())
    }

    @Test
    fun `toSentenceCase with non-ASCII characters`() {
        // Assuming default Locale makes é -> É
        val original = "éalpa"
        val expected = "Éalpa"
        // Ensure the test runs with a consistent locale or mock it if behavior is locale-dependent
        // For this specific function, it uses Locale.getDefault()
        // If specific behavior for other locales is needed, those would be separate tests.
        assertEquals(expected, original.toSentenceCase())

        val original2 = "你好世界" // Ni Hao Shi Jie (Hello World in Chinese)
        // Behavior for non-alphabetic scripts might be identity or based on Unicode case mappings.
        // The current implementation will lowercase then uppercase first char if it has a case.
        // Chinese characters generally don't have upper/lower case.
        assertEquals(original2, original2.toSentenceCase())
    }

    private val oneHourInMillis = TimeUnit.HOURS.toMillis(1)
    private val oneSecondInMillis = TimeUnit.SECONDS.toMillis(1)

    @Test
    fun `isOlderThanOneHour returns false for timestamp exactly one hour old`() {
        val currentTime = System.currentTimeMillis()
        val timestamp = currentTime - oneHourInMillis
        assertFalse(timestamp.isOlderThanOneHour(), "Timestamp exactly one hour old should not be considered older")
    }

    @Test
    fun `isOlderThanOneHour returns false for timestamp just under one hour old`() {
        val currentTime = System.currentTimeMillis()
        val timestamp = currentTime - oneHourInMillis + oneSecondInMillis // 59 mins 59 secs ago
        assertFalse(timestamp.isOlderThanOneHour(), "Timestamp 59m59s old should not be considered older")
    }

    @Test
    fun `isOlderThanOneHour returns true for timestamp just over one hour old`() {
        val currentTime = System.currentTimeMillis()
        val timestamp = currentTime - oneHourInMillis - oneSecondInMillis // 1 hour 1 sec ago
        assertTrue(timestamp.isOlderThanOneHour(), "Timestamp 1h1s old should be considered older")
    }

    @Test
    fun `isOlderThanOneHour returns true for timestamp significantly older than one hour`() {
        val twoHoursInMillis = TimeUnit.HOURS.toMillis(2)
        val currentTime = System.currentTimeMillis()
        val timestamp = currentTime - twoHoursInMillis
        assertTrue(timestamp.isOlderThanOneHour(), "Timestamp 2 hours old should be considered older")
    }

    @Test
    fun `isOlderThanOneHour returns false for current time timestamp`() {
        val timestamp = System.currentTimeMillis()
        assertFalse(timestamp.isOlderThanOneHour(), "Current timestamp should not be considered older")
    }

    @Test
    fun `isOlderThanOneHour returns false for future timestamp`() {
        val currentTime = System.currentTimeMillis()
        val timestamp = currentTime + oneHourInMillis // Timestamp 1 hour in the future
        assertFalse(timestamp.isOlderThanOneHour(), "Future timestamp should not be considered older")
    }
}
