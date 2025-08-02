package com.sarmad.moody.data.network.datasource

import android.util.Log
import com.sarmad.moody.BuildConfig
import com.sarmad.moody.core.util.toCelsius
import com.sarmad.moody.data.core.NetworkError
import com.sarmad.moody.data.core.dto.WeatherResponse
import com.sarmad.moody.domain.dispatcher.CoroutineDispatcherProvider
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.withContext
import kotlinx.io.IOException
import javax.inject.Inject

class OpenWeatherMapDataSource @Inject constructor(
    private val dispatcherProvider: CoroutineDispatcherProvider,
    private val httpClient: HttpClient,
) : WeatherNetworkDataSource {

    private val tag = OpenWeatherMapDataSource::class.java.simpleName

    override suspend fun getWeather(latitude: Double, longitude: Double): Result<WeatherResponse> =
        withContext(dispatcherProvider.io) {
            try {
                val url = "https://api.openweathermap.org/data/2.5/weather?" +
                        "lat=$latitude&lon=$longitude&appid=${BuildConfig.OPEN_WEATHER_MAP_API_KEY}"
                val response = httpClient.get(
                    urlString = url
                )

                when (response.status.value) {
                    in 200..299 -> {
                        try {
                            Log.d(
                                tag, "getWeather = ${response.status.value}, " +
                                        "response = ${response.body<WeatherResponse>()}"
                            )

                            val serializedResponse = response.body<WeatherResponse>()
                            val sanitizedResponse = serializedResponse.copy(
                                main = serializedResponse.main.copy(
                                    temp = serializedResponse.main.temp.toCelsius(),
                                    feelsLike = serializedResponse.main.feelsLike.toCelsius(),
                                    tempMin = serializedResponse.main.tempMin.toCelsius(),
                                    tempMax = serializedResponse.main.tempMax.toCelsius()
                                )
                            )

                            Result.success(value = sanitizedResponse)
                        } catch (e: Exception) {
                            Result.failure(exception = NetworkError.Parsing(reason = e))
                        }
                    }

                    401 -> Result.failure(exception = NetworkError.Unauthorized)
                    404 -> Result.failure(exception = NetworkError.NotFound)
                    in 500..599 -> Result.failure(exception = NetworkError.Server)
                    else -> Result.failure(exception = NetworkError.Unknown)
                }
            } catch (_: IOException) {
                Result.failure(exception = NetworkError.Network)
            } catch (_: Exception) {
                Result.failure(exception = NetworkError.Unknown)
            }
        }
}
