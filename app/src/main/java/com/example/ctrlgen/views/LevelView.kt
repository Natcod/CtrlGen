package com.example.ctrlgen.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.example.ctrlgen.R
import android.graphics.Color as AndroidColor
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt
import com.example.ctrlgen.utils.CustomValueFormatter
import androidx.compose.ui.res.colorResource

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

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .padding(16.dp)
            .wrapContentSize()
    ) {
        Text(
            text = label,
            color = Color.Black,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp) // Adjust vertical spacing here
        )
        Card(
            modifier = Modifier
                .width(250.dp),
            elevation = CardDefaults.cardElevation(1.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier

                    .background(colorResource(id = R.color.varC))
            ) {
                AndroidView(
                    modifier = Modifier.size(185.dp),
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
                Image(
                    painter = painterResource(id = R.drawable.red_drop),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(24.dp)
                        .offset {
                            IntOffset(x = 0, y = (chartSize / 2).toPx().roundToInt() - 105) // Adjust vertical offset here
                        }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLevelView() {
    MaterialTheme {
        LevelView(
            label = "Fuel Level",
            value = 65f,  // Example value
            chartSize = 150.dp,  // Example size
            sliceSpace = 3f,  // Example slice space
            holeRadius = 58f,  // Example hole radius
            transparentCircleRadius = 61f,  // Example transparent circle radius
            holeColor = Color.White.toArgb(),  // Example hole color
            centerTextSize = 22f,  // Example center text size
            modifier = Modifier.padding(16.dp)
        )
    }
}
