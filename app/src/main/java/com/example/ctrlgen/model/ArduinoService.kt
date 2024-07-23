package com.example.ctrlgen.model

import retrofit2.http.GET

interface ArduinoService {
    @GET("/sensor/data")
    suspend fun getSensorData(): SensorData
}
