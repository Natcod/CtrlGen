package com.example.ctrlgen

// ArduinoService.kt
import retrofit2.http.GET

interface ArduinoService {
    @GET("/sensor/data") // Adjust endpoint path as per your API
    suspend fun getSensorData(): SensorData // Adjust return type as per your API response
}
