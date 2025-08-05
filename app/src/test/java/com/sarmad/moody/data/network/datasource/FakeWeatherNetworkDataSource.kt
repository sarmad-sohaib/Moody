package com.sarmad.moody.data.network.datasource

import com.sarmad.moody.core.util.toCelsius
import com.sarmad.moody.data.core.NetworkError
import com.sarmad.moody.data.core.dto.Coord
import com.sarmad.moody.data.core.dto.Main
import com.sarmad.moody.data.core.dto.Weather
import com.sarmad.moody.data.core.dto.WeatherResponse

class FakeWeatherNetworkDataSource(
    private val scenario: Scenario,
) : WeatherNetworkDataSource {

    enum class Scenario {
        SUCCESS,
        UNAUTHORIZED,
        NOT_FOUND,
        SERVER_ERROR,
        NETWORK_ERROR,
        PARSING_ERROR,
        UNKNOWN_ERROR
    }

    override suspend fun getWeather(
        latitude: Double,
        longitude: Double,
    ) = when (scenario) {
        Scenario.SUCCESS -> {
            val fakeResponse = WeatherResponse(
                name = "London",
                coord = Coord(
                    lon = latitude,
                    lat = longitude,
                ),
                main = Main(
                    temp = 100.52.toCelsius(),
                ),
                weather = listOf(
                    Weather(
                        description = "cloudy",
                    )
                )
            )
            Result.success(value = fakeResponse)
        }

        Scenario.UNAUTHORIZED -> Result.failure(NetworkError.Unauthorized)
        Scenario.NOT_FOUND -> Result.failure(NetworkError.NotFound)
        Scenario.SERVER_ERROR -> Result.failure(NetworkError.Server)
        Scenario.NETWORK_ERROR -> Result.failure(NetworkError.Network)
        Scenario.PARSING_ERROR -> Result.failure(NetworkError.Parsing(Exception("Parsing failed")))
        Scenario.UNKNOWN_ERROR -> Result.failure(NetworkError.Unknown)
    }
}
