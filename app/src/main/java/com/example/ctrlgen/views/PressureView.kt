package com.example.ctrlgen.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PressureView(label: String, value: Float, modifier: Modifier = Modifier) {
    LineChartView(label, listOf(value), modifier)
}
