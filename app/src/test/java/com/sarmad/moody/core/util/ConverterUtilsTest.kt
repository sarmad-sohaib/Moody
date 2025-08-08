package com.sarmad.moody.core.util

import com.sarmad.moody.data.core.dto.Main
import com.sarmad.moody.data.core.dto.Weather
import com.sarmad.moody.data.core.dto.WeatherResponse
import com.sarmad.moody.data.local.entity.WeatherEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ConverterUtilsTest {
    private lateinit var testTimeProvider: TimeProvider

    @BeforeEach
    fun setup() {
        testTimeProvider = FakeTimeProvider()
    }

    @Test
    fun `WeatherResponse toDomain maps fields correctly`() {
        val response = WeatherResponse(
            name = "London",
            main = Main(temp = 20.5),
            weather = listOf(
                Weather(
                    description = "Sunny",
                )
            )
        )

        val domain = response.toDomain()

        assertEquals("London", domain.location)
        assertEquals(20.5, domain.temperature, 0.0)
        assertEquals("Sunny", domain.description)
    }

    @Test
    fun `WeatherEntity toDomain maps fields correctly`() {
        val entity = WeatherEntity(
            id = 1,
            updatedAt = testTimeProvider.currentTimeMillis(),
            location = "Paris",
            temperature = 18.2,
            description = "Cloudy"
        )

        val domain = entity.toDomain()

        assertEquals("Paris", domain.location)
        assertEquals(18.2, domain.temperature, 0.0)
        assertEquals("Cloudy", domain.description)
    }

    @Test
    fun `WeatherResponse toEntity uses provided time`() {
        val response = WeatherResponse(
            name = "Berlin",
            main = Main(temp = 25.0),
            weather = listOf(
                Weather(
                    description = "Sunny",
                )
            )
        )

        val entity = response.toEntity(
            alreadySavedId = null,
            currentTimeInMillis = testTimeProvider.currentTimeMillis()
        )

        assertEquals(1, entity.id)
        assertEquals(testTimeProvider.currentTimeMillis(), entity.updatedAt)
        assertEquals("Berlin", entity.location)
        assertEquals(25.0, entity.temperature, 0.0)
        assertEquals("Sunny", entity.description)
    }

    @Test
    fun `WeatherResponse toEntity respects alreadySavedId`() {
        val response = WeatherResponse(
            name = "Tokyo",
            main = Main(temp = 30.0),
            weather = listOf(
                Weather(
                    description = "Sunny",
                )
            )
        )

        val entity = response.toEntity(
            alreadySavedId = 42,
            currentTimeInMillis = testTimeProvider.currentTimeMillis(),
        )

        assertEquals(42, entity.id)
    }
}
