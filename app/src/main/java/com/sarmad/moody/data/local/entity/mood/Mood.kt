package com.sarmad.moody.data.local.entity.mood

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Mood(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val mood: String,
    val weatherDescription: String,
    val moodIcon: String,
    val createdAt: Long,
) {
    fun toFormattedDate(): String {
        val date = Date(createdAt)
        val formatter = java.text.SimpleDateFormat("dd MMM yyyy", java.util.Locale.getDefault())
        return formatter.format(date)
    }
}