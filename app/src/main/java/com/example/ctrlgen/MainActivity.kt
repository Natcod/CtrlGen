package com.example.ctrlgen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ctrlgen.controller.MainViewModel
import com.example.ctrlgen.views.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val viewModel = remember { MainViewModel() } // Create MainViewModel manually
    val sensorData by viewModel.sensorData

    Scaffold(
        bottomBar = { BottomNavigationBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEFF2F7))
                .verticalScroll(rememberScrollState())
                .padding(paddingValues), // Use paddingValues from Scaffold
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Generator Control", style = MaterialTheme.typography.h4)
            Spacer(modifier = Modifier.height(16.dp))

            if (sensorData.isPlaceholder) {
                Text(
                    text = "No data available. Showing placeholder values.",
                    style = MaterialTheme.typography.body1
                )

                // First row: OilLevelView and FuelLevelView
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(0.dp) // Adjust spacing here
                ) {
                    OilLevelView(
                        label = "Oil Level",
                        value = sensorData.oilLevel.last(),
                        modifier = Modifier.weight(1f)
                    )
                    FuelLevelView(
                        label = "Fuel Level",
                        value = sensorData.fuelLevel.last(),
                        modifier = Modifier.weight(1f)
                    )
                }

                // Second row: TemperatureView
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start

                ) {
                    TemperatureView("Temperature", sensorData.temperature)
                }

                // Third row: CurrentView and VoltageView
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CurrentView("Current", sensorData.current)
                    Spacer(modifier = Modifier.width(16.dp))
                    VoltageView("Voltage", sensorData.voltage)
                }

                // Fourth row: PressureView
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    PressureView("Pressure", sensorData.pressure)
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar() {
    var isGeneratorOn by remember { mutableStateOf(false) }

    BottomNavigation(
        backgroundColor = Color(0xFFCFD1D5),
        contentColor = Color.White
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home", color = Color.Black) },
            selected = false,
            onClick = { /* Navigate to Home */ }
        )
        BottomNavigationItem(
            icon = {
                Switch(
                    checked = isGeneratorOn,
                    onCheckedChange = {
                        isGeneratorOn = it
                        // Implement generator control logic here
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color.Green,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = Color.Red
                    )
                )
            },
            label = { Text("Control",color = Color.Black) },
            selected = false,
            onClick = { /* Do nothing on click */ }
        )
        BottomNavigationItem(
            icon = {
                Row {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Arrow Back")
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(Icons.Default.ArrowForward, contentDescription = "Arrow Forward")
                }
            },
            label = { Text("Connect", color = Color.Black) },
            selected = false,
            onClick = { /* Navigate to Connect */ }
        )
    }
}

@Preview
@Composable
fun PreviewMyApp() {
    MyApp()
}
