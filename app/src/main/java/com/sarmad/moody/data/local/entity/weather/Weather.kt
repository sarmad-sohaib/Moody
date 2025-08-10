package com.sarmad.moody.data.local.entity.weather

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherEntity(
    @PrimaryKey
    val id: Int,
    val location: String,
    val temperature: Double,
    val description: String,
    val updatedAt: Long,
)
