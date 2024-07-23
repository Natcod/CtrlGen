package com.example.ctrlgen.views

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ctrlgen.R

@Composable
fun CustomGauge(
    value: Float,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val sweepAngle = value / 100 * 180
        drawArc(
            color = Color.Gray,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            style = Stroke(width = 20f)
        )
        drawArc(
            color = Color.Green,
            startAngle = 180f,
            sweepAngle = sweepAngle,
            useCenter = false,
            style = Stroke(width = 20f)
        )
    }
}

@Composable
fun GaugeView(
    label: String,
    value: Float,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(18.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            color = Color.Black,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp) // Adjust vertical spacing here
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.varC))
                .padding(0.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            shape = RoundedCornerShape(8.dp)

        ) {
            Column(
                modifier = Modifier

                    .background(colorResource(id = R.color.varC)), // Set the background color using resource
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 66.dp, end = 16.dp, bottom = 0.dp)

                ) {
                    CustomGauge(value = value, modifier = Modifier.size(150.dp))
                    Text(
                        text = value.toInt().toString(),
                        color = Color.Black,
                        fontSize = 24.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGaugeView() {
    MaterialTheme {
        GaugeView(
            label = "Pressure",
            value = 22f // Example value
        )
    }
}
