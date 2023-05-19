package com.example.weather.response

data class WeatherItem(val icon: String = "",
                       val description: String = "",
                       val main: String = "",
                       val id: Int = 0)