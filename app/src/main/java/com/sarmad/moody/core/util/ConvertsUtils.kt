package com.sarmad.moody.core.util

import com.sarmad.moody.data.core.dto.WeatherResponse
import com.sarmad.moody.data.local.entity.weather.WeatherEntity
import com.sarmad.moody.domain.model.weather.Weather

// Excellent: Idiomatically written util functions for Kotlin
fun WeatherResponse.toDomain() = Weather(
    location = name,
    temperature = main.temp,
    description = weather.firstOrNull()?.description ?: ""
)

fun WeatherEntity.toDomain() = Weather(
    location = location,
    temperature = temperature,
    description = description
)

fun WeatherResponse.toEntity(
    alreadySavedId: Int?,
    currentTimeInMillis: Long,
) = WeatherEntity(
    updatedAt = currentTimeInMillis,
    id = alreadySavedId ?: 1,
    location = name,
    temperature = main.temp,
    description = weather.firstOrNull()?.description ?: ""
)
