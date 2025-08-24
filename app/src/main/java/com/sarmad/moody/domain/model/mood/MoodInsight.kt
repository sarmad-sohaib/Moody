package com.sarmad.moody.domain.model.mood

data class MoodInsight(
    val summary: String,
    val mood: String, // Bad: This should be an enum
    val weather: String, // Bad: This should be weather object, a "string" weather does not make sense OR it should be an enum of the possible values of weather types
    val frequency: Int
)

enum class MoodType {
    HAPPY, SAD, ANGRY
}