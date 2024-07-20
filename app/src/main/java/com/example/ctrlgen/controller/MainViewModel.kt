package com.example.ctrlgen.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.example.ctrlgen.network.RetrofitInstance
import com.example.ctrlgen.model.SensorData
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MainViewModel : ViewModel() {
    private val _sensorData = mutableStateOf(
        SensorData(
            oilLevel = listOf(20f),
            fuelLevel = listOf(50f),
            temperature = listOf(30f, 62f, 35f, 103f, 31f, 50f),
            current = 10f,
            voltage = 220f,
            pressure = 55f,
            powerRating = 220f,
            isPlaceholder = true
        )
    )
    val sensorData: State<SensorData> = _sensorData

    fun fetchSensorData(baseUrl: String) {
        viewModelScope.launch {
            try {
                val api = RetrofitInstance.getInstance(baseUrl)
                val data = api.getSensorData()
                _sensorData.value = data.copy(isPlaceholder = false)
            } catch (e: IOException) {
                // Handle network error
            } catch (e: HttpException) {
                // Handle HTTP error
            }
        }
    }
}
