package com.example.ctrlgen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ctrlgen.controller.MainViewModel
import com.example.ctrlgen.views.*

@Composable
fun HomeScreen(navController: NavHostController) {
    val viewModel = remember { MainViewModel() }
    val sensorData by viewModel.sensorData

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFF2F7))
            .verticalScroll(rememberScrollState())
            .padding(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top // Changed from Center to Top
    ) {
        Text(text = "Generator Control", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(16.dp))

        if (sensorData.isPlaceholder) {
            Text(
                text = "No data available. Showing placeholder values.",
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(16.dp))

            // First row with Oil Level and Fuel Level
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween // Adjusted arrangement
            ) {
                OilLevelView(
                    label = "Oil Level",
                    value = sensorData.oilLevel.last(),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                FuelLevelView(
                    label = "Fuel Level",
                    value = sensorData.fuelLevel.last(),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Temperature row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween // Adjusted arrangement
            ) {
                TemperatureView("Temperature", sensorData.temperature)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Row with Power Rating, Current, and Voltage
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween // Adjusted arrangement
            ) {
                PowerRatingView("Power Rating", sensorData.powerRating, modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(10.dp))
                CurrentView("Current Value", sensorData.current, modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(10.dp))
                VoltageView("Voltage Value", sensorData.voltage, modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Pressure view
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                PressureView("Pressure", sensorData.pressure)
            }
        }
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen(navController = NavHostController(LocalContext.current))
}
