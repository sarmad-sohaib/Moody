package com.sarmad.moody.core.util

import com.sarmad.moody.data.core.dto.WeatherResponse
import com.sarmad.moody.data.local.entity.WeatherEntity
import com.sarmad.moody.domain.dataholder.Weather

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

fun WeatherResponse.toEntity(alreadySavedId: Int?) = WeatherEntity(
    updatedAt = System.currentTimeMillis(),
    id = alreadySavedId ?: 1,
    location = name,
    temperature = main.temp,
    description = weather.firstOrNull()?.description ?: ""
)
