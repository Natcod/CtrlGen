package com.example.ctrlgen.controller

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ctrlgen.network.RetrofitInstance
import com.example.ctrlgen.model.SensorData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MainViewModel : ViewModel() {
    private val _sensorData = MutableStateFlow(
        SensorData(
            oilLevel = listOf(20f),
            fuelLevel = listOf(50f),
            temperature = listOf(30f),
            current = 10f,
            voltage = 220f,
            pressure = 55f,
            powerRating = 220f,
            isPlaceholder = true
        )
    )
    val sensorData: StateFlow<SensorData> = _sensorData

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private var ipAddress: String? = null

    fun fetchSensorData(baseUrl: String) {
        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val api = RetrofitInstance.getInstance(baseUrl)
                val data = api.getSensorData()
                _sensorData.value = data.copy(isPlaceholder = false)
                Log.d("MainViewModel", "Data received: $data")
            } catch (e: IOException) {
                // Handle network error
                _sensorData.value = _sensorData.value.copy(isPlaceholder = true)
                _errorMessage.value = "Network error: Unable to connect"
                Log.e("MainViewModel", "Network error: ${e.message}")
            } catch (e: HttpException) {
                // Handle HTTP error
                _sensorData.value = _sensorData.value.copy(isPlaceholder = true)
                _errorMessage.value = "HTTP error: ${e.message()}"
                Log.e("MainViewModel", "HTTP error: ${e.message()}")
            } catch (e: Exception) {
                // Handle other errors
                _sensorData.value = _sensorData.value.copy(isPlaceholder = true)
                _errorMessage.value = "Error: ${e.message}"
                Log.e("MainViewModel", "Error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun setIpAddress(ip: String) {
        ipAddress = ip
    }

    fun getIpAddress(): String? {
        return ipAddress
    }
}
