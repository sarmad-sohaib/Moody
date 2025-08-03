package com.sarmad.moody.core.util

import java.util.Locale
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