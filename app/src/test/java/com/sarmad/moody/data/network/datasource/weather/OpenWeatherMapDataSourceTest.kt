package com.sarmad.moody.data.network.datasource.weather

import com.sarmad.moody.data.core.errortype.NetworkError
import com.sarmad.moody.domain.dispatcher.FakeDispatcherProvider
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OpenWeatherMapDataSourceTest {

    @Test
    fun `returns success when status is 200`() = runTest {
        val jsonResponse = """
            {
              "main": {
                "temp": 300.0,
                "feels_like": 303.0,
                "temp_min": 298.0,
                "temp_max": 305.0
              }
            }
        """.trimIndent()

        val client = createMockHttpClient(200, jsonResponse)
        val testDispatcher = FakeDispatcherProvider(scheduler = testScheduler)
        val dataSource = OpenWeatherMapDataSource(testDispatcher, client)

        val result = dataSource.getWeather(10.0, 20.0)
        assertTrue(result.isSuccess)

        val weather = result.getOrNull()
        assertEquals(26.85, weather?.main?.temp ?: 0.0, 0.01) // Kelvin to Celsius
    }

    @Test
    fun `returns unauthorized when status is 401`() = runTest {
        val client = createMockHttpClient(401, "{}")
        val testDispatcher = FakeDispatcherProvider(scheduler = testScheduler)
        val dataSource = OpenWeatherMapDataSource(testDispatcher, client)

        val result = dataSource.getWeather(10.0, 20.0)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is NetworkError.Unauthorized)
    }

    @Test
    fun `returns not found when status is 404`() = runTest {
        val client = createMockHttpClient(404, "{}")
        val testDispatcher = FakeDispatcherProvider(scheduler = testScheduler)
        val dataSource = OpenWeatherMapDataSource(testDispatcher, client)

        val result = dataSource.getWeather(10.0, 20.0)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is NetworkError.NotFound)
    }

    @Test
    fun `returns server error when status is 500`() = runTest {
        val client = createMockHttpClient(500, "{}")
        val testDispatcher = FakeDispatcherProvider(scheduler = testScheduler)
        val dataSource = OpenWeatherMapDataSource(testDispatcher, client)

        val result = dataSource.getWeather(10.0, 20.0)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is NetworkError.Server)
    }

    @Test
    fun `returns unknown error for unexpected status`() = runTest {
        val client = createMockHttpClient(418, "{}") // I'm a teapot
        val testDispatcher = FakeDispatcherProvider(scheduler = testScheduler)
        val dataSource = OpenWeatherMapDataSource(testDispatcher, client)

        val result = dataSource.getWeather(10.0, 20.0)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is NetworkError.Unknown)
    }

    @Test
    fun `returns network error on IOException`() = runTest {
        val client = createMockHttpClient(
            200, "{}",
            _root_ide_package_.kotlinx.io.IOException("Network down")
        )
        val testDispatcher = FakeDispatcherProvider(scheduler = testScheduler)
        val dataSource = OpenWeatherMapDataSource(testDispatcher, client)

        val result = dataSource.getWeather(10.0, 20.0)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is NetworkError.Network)
    }

    @Test
    fun `returns unknown error on generic exception`() = runTest {
        val client = createMockHttpClient(200, "{}", RuntimeException("Something broke"))
        val testDispatcher = FakeDispatcherProvider(scheduler = testScheduler)
        val dataSource = OpenWeatherMapDataSource(testDispatcher, client)

        val result = dataSource.getWeather(10.0, 20.0)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is NetworkError.Unknown)
    }
}

fun createMockHttpClient(
    statusCode: Int,
    body: String,
    throwException: Throwable? = null,
): HttpClient {
    val mockEngine = MockEngine { request ->
        throwException?.let { throw it }

        respond(
            content = body,
            status = HttpStatusCode.fromValue(statusCode),
            headers = headersOf(HttpHeaders.ContentType, "application/json")
        )
    }

    return HttpClient(mockEngine) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }
}