package com.example.ctrlgen.views
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ctrlgen.R
import androidx.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun FuelLevelView(
    label: String = "Fuel Level",
    value: Float = 50f,
    modifier: Modifier = Modifier,
    chartSize: Dp = 200.dp
) {
    val sliceSpace = 3f
    val holeRadius = 90f
    val transparentCircleRadius = 55f
    val holeColor = colorResource(id = R.color.varC).toArgb()
    val centerTextSize = 24f

    LevelView(
        label = label,
        value = value,
        chartSize = chartSize,
        sliceSpace = sliceSpace,
        holeRadius = holeRadius,
        transparentCircleRadius = transparentCircleRadius,
        holeColor = holeColor,
        centerTextSize = centerTextSize,
        modifier = modifier
    )
}

