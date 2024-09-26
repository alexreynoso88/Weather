package com.virtusa.weather.sealed

import com.virtusa.weather
.model.weather.WeatherResponse

sealed class WeatherUiState {
    object Loading : WeatherUiState()
    data class Success(val weather: WeatherResponse) : WeatherUiState()
    data class Error(val exception: Throwable) : WeatherUiState()
}