package com.sarmad.moody.core.util

import java.util.Locale
import java.util.concurrent.TimeUnit
import kotlin.math.round

/**
 * Converts a Double from Kelvin to Celsius.
 */
fun Double.toCelsius(): Double {
    val celsiusValue = this - 273.15
    val multiplier = 100.0
    return round(celsiusValue * multiplier) / multiplier
}

fun String.toSentenceCase(): String {
    return this.lowercase(Locale.getDefault()).replaceFirstChar { it.uppercase() }
}

/**
 * Checks if this Long timestamp (in milliseconds since epoch) is older than 1 hour
 * compared to the current system time.
 *
 * @return True if the timestamp is older than 1 hour, false otherwise.
 */
fun Long.isOlderThanOneHour(): Boolean {
    val oneHourInMillis = TimeUnit.HOURS.toMillis(1)
    val currentTimeInMillis = System.currentTimeMillis()

    // If 'this' timestamp plus one hour is still less than the current time,
    // it means 'this' timestamp is older than one hour.
    return (this + oneHourInMillis) < currentTimeInMillis
}
