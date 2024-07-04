package com.example.ctrlgen.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.example.ctrlgen.R
import android.graphics.Color as AndroidColor
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt
import com.example.ctrlgen.utils.CustomValueFormatter

@Composable
fun LevelView(
    label: String,
    value: Float,
    chartSize: Dp,
    sliceSpace: Float,
    holeRadius: Float,
    transparentCircleRadius: Float,
    holeColor: Int,
    centerTextSize: Float,
    modifier: Modifier = Modifier
) {
    val percentageText = "${value.toInt()}%"

    val complementValue = 100f - value

    val colorRemaining = Color.White.toArgb()
    val colorUsed = Color.Red.toArgb()

    Box(
        modifier = modifier.padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(chartSize),
            factory = { context ->
                PieChart(context).apply {
                    description.isEnabled = false
                    legend.isEnabled = false
                    setTouchEnabled(false)
                    setHoleRadius(holeRadius)
                    setTransparentCircleRadius(transparentCircleRadius)
                    centerText = percentageText
                    setCenterTextSize(centerTextSize)
                    setHoleColor(holeColor)

                    val entries = listOf(
                        PieEntry(value),
                        PieEntry(complementValue)
                    )
                    val dataSet = PieDataSet(entries, "").apply {
                        setColors(colorUsed, colorRemaining)
                        valueTextColor = AndroidColor.TRANSPARENT
                        valueTextSize = 16f
                        this.sliceSpace = sliceSpace
                    }

                    data = PieData(dataSet).apply {
                        setValueFormatter(CustomValueFormatter())
                    }
                }
            },
            update = { chart ->
                val entries = listOf(
                    PieEntry(value),
                    PieEntry(complementValue)
                )
                val dataSet = PieDataSet(entries, "").apply {
                    setColors(colorUsed, colorRemaining)
                    valueTextColor = AndroidColor.TRANSPARENT
                    valueTextSize = 16f
                    this.sliceSpace = sliceSpace
                }

                chart.centerText = percentageText
                chart.data = PieData(dataSet).apply {
                    setValueFormatter(CustomValueFormatter())
                }
                chart.invalidate()
            }
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.matchParentSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.red_drop),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(34.dp)
                    .offset {
                        IntOffset(x = 0, y = (-chartSize / 2).toPx().roundToInt() + 100)
                    }
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = label,
        color = Color.Black
    )
}
