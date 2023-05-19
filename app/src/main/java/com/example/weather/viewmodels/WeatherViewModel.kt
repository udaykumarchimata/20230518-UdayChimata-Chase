package com.example.weather.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.Repository
import com.example.weather.response.Weather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel
@Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableLiveData<Result<Weather>>()
    val uiState: LiveData<Result<Weather>>
        get() = _uiState

    private val _isLoading = mutableStateOf(false)
    val isLoading: MutableState<Boolean>
        get() = _isLoading

    private val _showCityInput = mutableStateOf(false)
    val showCityInput: MutableState<Boolean>
        get() = _showCityInput

    fun getWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val result = repository.getWeather(latitude, longitude)
                _uiState.value = Result.success(result)
                _isLoading.value = false
            } catch (exception: Exception) {
                _uiState.value = Result.failure(exception)
                _isLoading.value = false
            }
        }
    }

    fun getLocationCoordinates(cityName: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val listOfCity = repository.getLocationCoordinates(cityName)
                val initialCity = listOfCity[0]
                val result = repository.getWeather(initialCity.lat, initialCity.lon)
                _uiState.value = Result.success(result)
                _isLoading.value = false
                showCityInput.value=false
            } catch (exception: Exception) {
                _uiState.value = Result.failure(exception)
                _isLoading.value = false
                showCityInput.value=false
            }
        }
    }

    fun updateCityInput() {
        _showCityInput.value = true
    }
}