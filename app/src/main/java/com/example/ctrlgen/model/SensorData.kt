package com.example.ctrlgen.model

data class SensorData(
    val oilLevel: Float,
    val fuelLevel: Float,
    val temperature: Float,
    val current: Float,
    val voltage: Float,
    val pressure: Float,
    val isPlaceholder: Boolean = false // New flag to indicate placeholder data
)
