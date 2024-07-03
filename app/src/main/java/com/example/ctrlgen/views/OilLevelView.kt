import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
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

class CustomValueFormatter : ValueFormatter() {
    override fun getPieLabel(value: Float, pieEntry: PieEntry?): String {
        // Return an empty string to hide the individual slice values
        return ""
    }
}

@Preview
@Composable
fun OilLevelView(
    label: String = "Oil Level", value: Float = 50f, modifier: Modifier = Modifier, chartSize: Dp = 200.dp
) {
    val sliceSpace = 3f
    val holeRadius = 90f // Adjust the hole radius as needed
    val transparentCircleRadius = 55f // Adjust the transparent circle radius as needed
    val holeColor = colorResource(id = R.color.varC).toArgb() // Using R.color.var

    val percentageText = "${value.toInt()}%" // Display only the integer percentage value

    // Calculate the complement value (remaining percentage)
    val complementValue = 100f - value

    // Define colors for used and remaining sections
    val colorRemaining = Color.White.toArgb()
    val colorUsed = Color.Red.toArgb()




    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
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
                    setCenterTextSize(24f) // Adjust the text size as needed
                    setHoleColor(holeColor) // Set the hole color using R.color.var

                    val entries = listOf(
                        PieEntry(value),
                        PieEntry(complementValue)
                    )
                    val dataSet = PieDataSet(entries, "").apply {
                        setColors( colorUsed,colorRemaining ) // Set colors for used and remaining sections
                        valueTextColor = AndroidColor.TRANSPARENT // Hide text color by making it transparent
                        valueTextSize = 16f
                        this.sliceSpace = sliceSpace
                    }

                    data = PieData(dataSet).apply {
                        setValueFormatter(CustomValueFormatter()) // Use custom formatter to hide individual values
                    }
                }
            },
            update = { chart ->
                val entries = listOf(
                    PieEntry(value),
                    PieEntry(complementValue)
                )
                val dataSet = PieDataSet(entries, "").apply {
                    setColors(colorUsed,colorRemaining) // Set colors for used and remaining sections
                    valueTextColor = AndroidColor.TRANSPARENT // Hide text color by making it transparent
                    valueTextSize = 16f
                    this.sliceSpace = sliceSpace
                }

                chart.centerText = percentageText
                chart.data = PieData(dataSet).apply {
                    setValueFormatter(CustomValueFormatter()) // Use custom formatter to hide individual values
                }
                chart.invalidate()
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            color = Color.Black
        )
    }
}
