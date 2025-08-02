package com.sarmad.moody.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Mood(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val mood: String,
    val weatherDescription: String,
    val moodIcon: String,
    val createdAt: Long,
)
