package com.example.weather

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.weather.response.Weather

@Composable
fun ShowTemperatures(
    isLoading: Boolean, weather: Result<Weather>?,onClick:()->Unit
) {
    val openDialog = remember { mutableStateOf(true) }
    if (isLoading) {
        DisplayProgressDialog()
    } else {
        weather?.onSuccess { weather ->
            DisplayTemperature(weather.main.tempMin, weather.main.tempMax)
        }
        weather?.onFailure {
            DisplayAlertDialog(openDialog,onClick)
        }
    }
}


