package com.virtusa.weather.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.virtusa.weather
.model.weather.WeatherResponse
import com.virtusa.weather
.sealed.WeatherUiState
import com.virtusa.weather
.util.convertKelvinToF
import com.virtusa.weather
.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(modifier: Modifier, viewModel: WeatherViewModel = hiltViewModel()) {
    var city by remember { mutableStateOf("") }
    val weatherUiState by viewModel.weatherData.observeAsState()
    // I try to do some state in the button to have state if is press, to do not have a multi press issue later, but i didn't have many time to do it
    var isFetching by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Row(modifier = modifier.fillMaxWidth()) {

            TextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("City") },
                modifier = Modifier.weight(0.65f),
                singleLine = true
            )
            Spacer(modifier = Modifier.width(12.dp))
            Button(
                onClick = {
                    isFetching = true
                    viewModel.fetchWeatherData(city)
                },
                modifier = Modifier.weight(0.3f)
            ) {
                Text(text = "Search")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        when (weatherUiState) {
            is WeatherUiState.Success -> {
                WeatherDataDisplay(weatherData = (weatherUiState as WeatherUiState.Success).weather)
            }

            is WeatherUiState.Loading -> {
                // Show a loading indicator
                Text("Loading...")
            }

            is WeatherUiState.Error -> {
                // Show an error message
                Text("Error: ${(weatherUiState as WeatherUiState.Error).exception.message}")
            }

            else -> {} // Handle initial state or null
        }
    }
}

@Composable
fun WeatherDataDisplay(weatherData: WeatherResponse) {
    // with more time, I was thinking to do some lazyColumn to implemented a list with all the cities that return the first api
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Is use for download the image in a simple way, is possible to improve this component, if a have time a can maybe implemented some states to handle better
        AsyncImage(
            model = "https://openweathermap.org/img/wn/${weatherData.weather[0].icon}@2x.png",
            contentDescription = "",
            modifier = Modifier.size(100.dp)
        )
        Text(
            text = weatherData.name,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        // You can add all the information that is requires, only I used temp and humidity
        Text(
            text = "Temperature: ${((weatherData.main?.temp ?: 0.0).convertKelvinToF())}°F",
            style = MaterialTheme.typography.bodySmall
        )

        // Humidity
        Text(
            text = "Humidity: ${weatherData.main?.humidity}%",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
