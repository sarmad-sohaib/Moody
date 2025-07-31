package com.sarmad.moody.data.core.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @SerialName("coord") val coord: Coord = Coord(),
    @SerialName("weather") val weather: List<Weather> = emptyList(),
    @SerialName("base") val base: String = "",
    @SerialName("main") val main: Main = Main(),
    @SerialName("visibility") val visibility: Int = 0,
    @SerialName("wind") val wind: Wind = Wind(),
    @SerialName("clouds") val clouds: Clouds = Clouds(),
    @SerialName("dt") val dt: Long = 0L,
    @SerialName("sys") val sys: Sys = Sys(),
    @SerialName("timezone") val timezone: Int = 0,
    @SerialName("id") val id: Long = 0L,
    @SerialName("name") val name: String = "",
    @SerialName("cod") val cod: Int = 0,
)

@Serializable
data class Coord(
    @SerialName("lon") val lon: Double = 0.0,
    @SerialName("lat") val lat: Double = 0.0,
)

@Serializable
data class Weather(
    @SerialName("id") val id: Int = 0,
    @SerialName("main") val main: String = "",
    @SerialName("description") val description: String = "",
    @SerialName("icon") val icon: String = "",
)

@Serializable
data class Main(
    @SerialName("temp") val temp: Double = 0.0,
    @SerialName("feels_like") val feelsLike: Double = 0.0,
    @SerialName("temp_min") val tempMin: Double = 0.0,
    @SerialName("temp_max") val tempMax: Double = 0.0,
    @SerialName("pressure") val pressure: Int = 0,
    @SerialName("humidity") val humidity: Int = 0,
    @SerialName("sea_level") val seaLevel: Int = 0,
    @SerialName("grnd_level") val groundLevel: Int = 0,
)

@Serializable
data class Wind(
    @SerialName("speed") val speed: Double = 0.0,
    @SerialName("deg") val deg: Int = 0,
    @SerialName("gust") val gust: Double = 0.0,
)

@Serializable
data class Clouds(
    @SerialName("all") val all: Int = 0,
)

@Serializable
data class Sys(
    @SerialName("country") val country: String = "",
    @SerialName("sunrise") val sunrise: Long = 0L,
    @SerialName("sunset") val sunset: Long = 0L,
)
