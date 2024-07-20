package com.example.ctrlgen.model

data class SensorData(
    val oilLevel: List<Float>,
    val fuelLevel: List<Float>,
    val temperature: List<Float>,
    val current: Float,
    val voltage: Float,
    val pressure:Float,
    val powerRating:Float,
   val isPlaceholder: Boolean = false // New flag to indicate placeholder data
)
