package com.sarmad.moody.domain.model.weather

data class Weather(
    val location: String,
    val temperature: Double,
    val description: String,
)