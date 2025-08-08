package com.sarmad.moody.data.repository

import com.sarmad.moody.core.util.FakeTimeProvider
import com.sarmad.moody.data.local.datasource.FakeWeatherLocalDataSource
import com.sarmad.moody.data.local.entity.WeatherEntity
import com.sarmad.moody.data.network.datasource.FakeWeatherNetworkDataSource
import com.sarmad.moody.data.network.datasource.FakeWeatherNetworkDataSource.Scenario
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultWeatherRepositoryTest {
    private lateinit var networkDataSource: FakeWeatherNetworkDataSource
    private lateinit var localDataSource: FakeWeatherLocalDataSource
    private lateinit var repository: DefaultWeatherRepository
    private lateinit var fakeTimeProvider: FakeTimeProvider

    companion object {
        private const val TEST_LAT = 51.5074
        private const val TEST_LON = -0.1278
    }

    @BeforeEach
    fun setup() {
        localDataSource = FakeWeatherLocalDataSource()
        fakeTimeProvider = FakeTimeProvider()
    }

    @Test
    fun `emits network result and saves locally when local data is missing`() = runTest {
        networkDataSource = FakeWeatherNetworkDataSource(scenario = Scenario.SUCCESS)
        repository = DefaultWeatherRepository(
            weatherNetworkDataSource = networkDataSource,
            weatherLocalDataSource = localDataSource,
            timeProvider = fakeTimeProvider,
        )

        val result = repository.getWeather(latitude = TEST_LAT, longitude = TEST_LON).first()
        assertTrue(result.isSuccess)
        assertEquals("London", result.getOrNull()?.location)

        // Now check that the local data source received the saved value by collecting one item
        val savedLocal = localDataSource.getWeather().first()
        assertEquals("London", savedLocal?.location)
    }

    @Test
    fun `returns updated data when local weather is older than one hour`() = runTest {
        // Set up expired weather (over 1 hour ago)
        val expiredWeather = WeatherEntity(
            id = 8,
            location = "OldTown",
            description = "foggy",
            temperature = 14.2,
            updatedAt = (System.currentTimeMillis() - 3600_000 * 2) // 2 hours ago
        )
        localDataSource.setWeather(weather = expiredWeather)
        networkDataSource = FakeWeatherNetworkDataSource(scenario = Scenario.SUCCESS)
        repository = DefaultWeatherRepository(
            weatherNetworkDataSource = networkDataSource,
            weatherLocalDataSource = localDataSource,
            timeProvider = fakeTimeProvider,
        )

        val result = repository.getWeather(latitude = TEST_LAT, longitude = TEST_LON).first()
        assertTrue(result.isSuccess)
        assertEquals("London", result.getOrNull()?.location)
    }

    @Test
    fun `emits network error when fetching update fails with expired data`() = runTest {
        val expiredWeather = WeatherEntity(
            id = 1,
            location = "OldTown",
            description = "foggy",
            temperature = 14.2,
            updatedAt = (System.currentTimeMillis() - 3600_000 * 2) // 2 hours ago
        )
        localDataSource.setWeather(weather = expiredWeather)
        networkDataSource = FakeWeatherNetworkDataSource(scenario = Scenario.NOT_FOUND)
        repository = DefaultWeatherRepository(
            weatherNetworkDataSource = networkDataSource,
            weatherLocalDataSource = localDataSource,
            timeProvider = fakeTimeProvider,
        )

        val result = repository.getWeather(latitude = TEST_LAT, longitude = TEST_LON).first()
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() != null)
    }

    @Test
    fun `emits cached data when local data is fresh`() = runTest {
        val freshWeather = WeatherEntity(
            id = 2,
            location = "Recency",
            description = "warm",
            temperature = 18.5,
            updatedAt = System.currentTimeMillis() // Now
        )
        localDataSource.setWeather(weather = freshWeather)
        networkDataSource =
            FakeWeatherNetworkDataSource(scenario = Scenario.SUCCESS) // Should NOT be called
        repository = DefaultWeatherRepository(
            weatherNetworkDataSource = networkDataSource,
            weatherLocalDataSource = localDataSource,
            timeProvider = fakeTimeProvider,
        )
        val result = repository.getWeather(latitude = TEST_LAT, longitude = TEST_LON).first()
        assertTrue(result.isSuccess)
        assertEquals("Recency", result.getOrNull()?.location)
    }
}
