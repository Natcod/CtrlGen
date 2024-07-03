package com.example.ctrlgen.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.compose.ui.tooling.preview.Preview
import com.example.ctrlgen.network.RetrofitInstance
import com.example.ctrlgen.model.SensorData
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class MainViewModel : ViewModel() {
    private val _sensorData = mutableStateOf(
        SensorData(
            oilLevel = 20f,
            fuelLevel = 50f,
            temperature = 30f,
            current = 10f,
            voltage = 220f,
            pressure = 5f,
            isPlaceholder = true // Initially set as placeholder data
        )
    )
    val sensorData: State<SensorData> = _sensorData

    init {
        fetchSensorData()
    }

    private fun fetchSensorData() {
        viewModelScope.launch {
            try {
                val data = RetrofitInstance.api.getSensorData()
                _sensorData.value = data.copy(isPlaceholder = false)
            } catch (e: IOException) {
                // Handle network error
            } catch (e: HttpException) {
                // Handle HTTP error
            }
        }
    }
}
