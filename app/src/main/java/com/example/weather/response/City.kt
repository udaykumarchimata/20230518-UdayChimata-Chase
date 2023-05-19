package com.example.weather.response

data class City(val localNames: LocalNames,
                val country: String = "",
                val name: String = "",
                val lon: Double = 0.0,
                val lat: Double = 0.0)