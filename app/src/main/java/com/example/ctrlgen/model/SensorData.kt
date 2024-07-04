package com.example.ctrlgen.model

data class SensorData(
    val oilLevel: List<Float>,
    val fuelLevel: List<Float>,
    val temperature: List<Float>,
    val current: List<Float>,
    val voltage: List<Float>,
    val pressure: List<Float>,
    val isPlaceholder: Boolean = false // New flag to indicate placeholder data
)
