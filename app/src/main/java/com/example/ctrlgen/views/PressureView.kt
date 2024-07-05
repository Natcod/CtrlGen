package com.example.ctrlgen.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PressureView(label: String, value: Float, modifier: Modifier = Modifier) {
    GaugeView(label =label , value =value )
}
