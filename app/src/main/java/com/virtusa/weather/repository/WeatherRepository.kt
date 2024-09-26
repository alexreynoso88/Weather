package com.virtusa.weather.repository

import com.virtusa.weather
.model.coordinate.CoordResponse
import com.virtusa.weather
.model.weather.WeatherResponse
import com.virtusa.weather
.retrofit.WeatherService
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherService: WeatherService) {

    val API_KEY = "e748beade60d9b49a46657debaf81e03"

    suspend fun getWeather(lat: String, lon: String): WeatherResponse {
        return weatherService.getWeather(lat, lon, API_KEY)
    }

    suspend fun getCoordinatesByName(city: String): List<CoordResponse> {
        return weatherService.getCoordinatesByName(city, 1, API_KEY)
    }
}