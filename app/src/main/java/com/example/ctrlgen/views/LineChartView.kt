package com.example.ctrlgen.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

@Preview
@Composable
fun LineChartViewPreview() {
    val label = "Sample Chart"
    val values = listOf(20f, 40f, 60f, 80f, 100f)

    LineChartView(label = label, values = values)
}

@Composable
fun LineChartView(label: String, values: List<Float>, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        Text(text = label, color = Color.Black)
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(top = 8.dp),
            factory = { context ->
                LineChart(context).apply {
                    val entries = values.mapIndexed { index, value ->
                        Entry(index.toFloat(), value)
                    }
                    val dataSet = LineDataSet(entries, "Value").apply {
                        color = Color.Blue.toArgb()
                        valueTextColor = Color.Black.toArgb()
                        lineWidth = 2f
                        setDrawCircles(true)
                        circleRadius = 4f
                    }
                    data = LineData(dataSet)

                    description.isEnabled = false
                    legend.isEnabled = false

                    xAxis.apply {
                        setDrawGridLines(true) // Enable grid lines
                        gridLineWidth = 1.5f // Set grid line width
                        granularity = 1f // Set the minimum interval between grid lines
                        setDrawLabels(true)
                        position = XAxis.XAxisPosition.BOTTOM
                        textSize = 10f
                        textColor = Color.Black.toArgb()
                    }

                    axisLeft.apply {
                        setDrawGridLines(true) // Enable grid lines
                        gridLineWidth = 1.5f // Set grid line width
                        granularity = 50f // Set the minimum interval between grid lines to 50
                        setLabelCount(5, true) // Ensure there are 3 labels to have gaps of 50 units
                        textSize = 12f
                        axisLineWidth = 1.5f // Adjust axis line width
                        axisMinimum = 0f
                        axisMaximum = 100f
                    }

                    axisRight.isEnabled = false
                    setTouchEnabled(false)
                }
            },
            update = { chart ->
                val entries = values.mapIndexed { index, value ->
                    Entry(index.toFloat(), value)
                }
                val dataSet = LineDataSet(entries, "Value").apply {
                    color = Color.Blue.toArgb()
                    valueTextColor = Color.Black.toArgb()
                    lineWidth = 2f
                    setDrawCircles(true)
                    circleRadius = 4f
                }
                chart.data = LineData(dataSet)
                chart.invalidate()
            }
        )
    }
}
