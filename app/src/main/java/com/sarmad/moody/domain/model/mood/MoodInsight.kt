package com.sarmad.moody.domain.model.mood

data class MoodInsight(
    val summary: String,
    val mood: String,
    val weather: String,
    val frequency: Int
)