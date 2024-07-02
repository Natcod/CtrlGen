package com.example.ctrlgen.views
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

@Composable
fun OilLevelView(label: String, value: Float, modifier: Modifier = Modifier) {
    LineChartView(label, value, modifier)
}

@Composable
fun LineChartView(label: String, value: Float, modifier: Modifier = Modifier) {
    AndroidView(
        factory = { context ->
            LineChart(context).apply {
                description = Description().apply { text = label }
            }
        },
        update = { chart ->
            val entries = listOf(Entry(0f, value))
            val dataSet = LineDataSet(entries, label)
            chart.data = LineData(dataSet)
            chart.invalidate() // refresh chart
        },
        modifier = modifier
    )
}
