package com.example.weather.data


import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import com.example.weather.api.Service
import com.example.weather.response.City
import com.example.weather.response.Weather
import javax.inject.Inject

class Repository @Inject constructor(
    private val service: Service, private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getWeather(latitude: Double, longitude: Double): Weather {
        return withContext(ioDispatcher) {
            service.getWeatherData(latitude, longitude)
        }
    }

    suspend fun getLocationCoordinates(cityName: String): List<City> {
        return withContext(ioDispatcher) {
            service.getLocationCoordinates(cityName)
        }
    }
}