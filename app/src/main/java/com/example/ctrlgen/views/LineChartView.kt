package com.example.ctrlgen.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun LineChartView(label: String, value: Float, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        Text(text = label, color = Color.Black)
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            factory = { context ->
                LineChart(context).apply {
                    data = LineData(LineDataSet(listOf(Entry(0f, value)), "Value").apply {
                        color = Color.Blue.toArgb()
                        valueTextColor = Color.Black.toArgb()
                    })
                    description.isEnabled = false
                    xAxis.isEnabled = false
                    axisRight.isEnabled = false
                    axisLeft.axisMinimum = 0f
                    axisLeft.axisMaximum = 100f // Adjust this range as necessary
                    setTouchEnabled(false)
                }
            },
            update = { chart ->
                chart.data = LineData(LineDataSet(listOf(Entry(0f, value)), "Value").apply {
                    color = Color.Blue.toArgb()
                    valueTextColor = Color.Black.toArgb()
                })
                chart.invalidate()
            }
        )
    }
}
