package com.virtusa.weather.model.weather

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    var weather: ArrayList<Weather> = arrayListOf(),
    var main: Main? = Main(),
    var name: String
)

data class Weather(

    var id: Int? = null,
    var main: String? = null,
    var description: String? = null,
    var icon: String? = null

)

data class Main(
    var temp: Double? = null,
    @SerializedName("feels_like") var feelsLike: Double? = null,
    @SerializedName("temp_min") var tempMin: Double? = null,
    @SerializedName("temp_max") var tempMax: Double? = null,
    var pressure: Int? = null,
    var humidity: Int? = null,
    @SerializedName("sea_level") var seaLevel: Int? = null,
    @SerializedName("grnd_level") var grndLevel: Int? = null

)