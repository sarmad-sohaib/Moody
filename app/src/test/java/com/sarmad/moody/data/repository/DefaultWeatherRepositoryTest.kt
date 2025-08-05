package com.sarmad.moody.data.repository

import app.cash.turbine.test
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

    companion object {
        private const val TEST_LAT = 51.5074
        private const val TEST_LON = -0.1278
    }

    @BeforeEach
    fun setup() {
        localDataSource = FakeWeatherLocalDataSource()
    }

    @Test
    fun `emits network result and saves locally when local data is missing`() = runTest {
        networkDataSource = FakeWeatherNetworkDataSource(Scenario.SUCCESS)
        repository = DefaultWeatherRepository(networkDataSource, localDataSource)
        repository.getWeather(TEST_LAT, TEST_LON).test {
            val result = awaitItem()
            assertTrue(result.isSuccess)
            // Check location name from the mapped domain model (cityName or location)
            assertEquals("London", result.getOrNull()?.location)
            // Now check that the local data source received the saved value by collecting one item
            val savedLocal = localDataSource.getWeather().first()
            assertEquals("London", savedLocal?.location)
            awaitComplete()
        }
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
        localDataSource.setWeather(expiredWeather)
        networkDataSource = FakeWeatherNetworkDataSource(Scenario.SUCCESS)
        repository = DefaultWeatherRepository(networkDataSource, localDataSource)
        repository.getWeather(TEST_LAT, TEST_LON).test {
            val result = awaitItem()
            assertTrue(result.isSuccess)
            assertEquals("London", result.getOrNull()?.location)
            awaitComplete()
        }
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
        localDataSource.setWeather(expiredWeather)
        networkDataSource = FakeWeatherNetworkDataSource(Scenario.NOT_FOUND)
        repository = DefaultWeatherRepository(networkDataSource, localDataSource)
        repository.getWeather(TEST_LAT, TEST_LON).test {
            val result = awaitItem()
            assertTrue(result.isFailure)
            assertTrue(result.exceptionOrNull()?.message != null)
            awaitComplete()
        }
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
        localDataSource.setWeather(freshWeather)
        networkDataSource = FakeWeatherNetworkDataSource(Scenario.SUCCESS) // Should NOT be called
        repository = DefaultWeatherRepository(networkDataSource, localDataSource)
        repository.getWeather(TEST_LAT, TEST_LON).test {
            val result = awaitItem()
            assertTrue(result.isSuccess)
            assertEquals("Recency", result.getOrNull()?.location)
            awaitComplete()
        }
    }
}
