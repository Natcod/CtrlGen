package com.example.ctrlgen.screen

import android.content.Context
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.ctrlgen.controller.MainViewModel
import com.example.ctrlgen.views.*

@Composable
fun HomeScreen(navController: NavHostController) {
    val viewModel: MainViewModel = viewModel()
    val sensorData by viewModel.sensorData.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val context = LocalContext.current

    // Fetch IP address from shared preferences
    val sharedPreferences = context.getSharedPreferences("ip_prefs", Context.MODE_PRIVATE)
    val savedIpAddress = sharedPreferences.getString("ip_address", null)

    // Remember the IP address to avoid unnecessary recompositions
    val ipAddress = remember { savedIpAddress }

    // Function to trigger data fetch
    fun reloadData() {
        if (ipAddress != null) {
            viewModel.fetchSensorData("http://$ipAddress/")
        }
    }

    // Initial data fetch
    LaunchedEffect(Unit) {
        reloadData()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFF2F7))
            .verticalScroll(rememberScrollState())
            .padding(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Reload button
        Button(onClick = { reloadData() }) {
            Text("Reload Data")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Generator Control", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> {
                CircularProgressIndicator()
            }
            errorMessage != null -> {
                Text(
                    text = errorMessage ?: "",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            else -> {
                val showPlaceholder = sensorData.isPlaceholder || sensorData.oilLevel.isEmpty()

                if (showPlaceholder) {
                    Text(
                        text = "No data available. Showing placeholder values.",
                        style = MaterialTheme.typography.body1
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // First row with Oil Level and Fuel Level
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OilLevelView(
                        label = "Oil Level",
                        value = if (showPlaceholder) 0f else sensorData.oilLevel.last(),
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    FuelLevelView(
                        label = "Fuel Level",
                        value = if (showPlaceholder) 0f else sensorData.fuelLevel.last(),
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Temperature row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TemperatureView("Temperature", if (showPlaceholder) listOf(0f) else sensorData.temperature)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Row with Power Rating, Current, and Voltage
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    PowerRatingView("Power Rating", if (showPlaceholder) 0f else sensorData.powerRating, modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(10.dp))
                    CurrentView("Current Value", if (showPlaceholder) 0f else sensorData.current, modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(10.dp))
                    VoltageView("Voltage Value", if (showPlaceholder) 0f else sensorData.voltage, modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Pressure view
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    PressureView("Pressure", if (showPlaceholder) 0f else sensorData.pressure)
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen(navController = NavHostController(LocalContext.current))
}
