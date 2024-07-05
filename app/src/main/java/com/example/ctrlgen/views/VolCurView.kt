package com.example.ctrlgen.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.ctrlgen.R

@Composable
fun VolCurView(
    label: String,
    value: Float,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(26.dp)
            .wrapContentSize(),
        horizontalAlignment = Alignment.Start
    ) {
        // Label above the card
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 22.sp),
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        // Card with value
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .background(colorResource(id = R.color.varC))
                .padding(16.dp)
                .width(100.dp)
                .height(60.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = String.format("%.2f", value),
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 24.sp),
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview
@Composable
fun VolCurViewPreview() {
    VolCurView(
        label = "Current (A)",
        value = 23.00f
    )
}
