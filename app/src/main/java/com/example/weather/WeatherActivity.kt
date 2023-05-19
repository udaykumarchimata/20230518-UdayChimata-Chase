package com.example.weather

import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.provider.Settings
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import com.example.weather.viewmodels.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@AndroidEntryPoint
class WeatherActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val weatherViewModel by viewModels<WeatherViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    Log.i("ACCESS_FINE_LOCATION", "true")
                    // Precise location access granted.
                    getLocation()

                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                    Log.i("ACCESS_COARSE_LOCATION", "true")
                    getLocation()
                }
                else -> {
                    // No location access granted.
                    weatherViewModel.updateCityInput()
                }
            }
        }.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        setContent {
            val enteredText = remember {
                mutableStateOf(" ")
            }
            val enteredValue = SharedPreferencesManager.getStringInfo(
                this@WeatherActivity, getString(R.string.city_name)
            )
            if (!(enteredValue.isNullOrEmpty())) {
                enteredText.value = enteredValue
            }

            val isLoading by weatherViewModel.isLoading
            val isCityInputFieldVisible by weatherViewModel.showCityInput
            val weather by weatherViewModel.uiState.observeAsState()
            ShowTemperatures(isLoading, weather){
                weatherViewModel.updateCityInput()
            }

            if (isCityInputFieldVisible) {
                displayInputField(enteredText)
            }
        }
    }

    @Composable
    private fun displayInputField(enteredText: MutableState<String>) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(value = enteredText.value, onValueChange = { input: String ->
                enteredText.value = input
            }, label = {
                Text(text = stringResource(id = R.string.enter_city))
            })
            Button(onClick = {
                val enteredCity = enteredText.value
                SharedPreferencesManager.setStringInfo(
                    this@WeatherActivity, getString(R.string.city_name), enteredCity
                )
                weatherViewModel.getLocationCoordinates(enteredCity)
            }, modifier = Modifier.padding(top = 8.dp)) {
                Text(text = "Enter")
            }
        }
    }

    private fun getLocation() {
        if (isLocationEnabled()) {
            fusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                val location: Location? = task.result
                if (location != null) {
                    weatherViewModel.getWeather(location.latitude, location.longitude)
                }
            }
        } else {
            Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
}
