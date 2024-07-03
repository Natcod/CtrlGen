package com.example.ctrlgen

import OilLevelView
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import androidx.lifecycle.viewmodel.compose.viewModel
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

@Preview
@Composable
fun MyApp() {
    val viewModel = remember { MainViewModel() } // Create MainViewModel manually

    val sensorData by viewModel.sensorData

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(36.dp)
            .verticalScroll(rememberScrollState()),  // Enable scrolling
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

            OilLevelView("Oil Level", sensorData.oilLevel)
            FuelLevelView("Fuel Level", sensorData.fuelLevel)
            TemperatureView("Temperature", sensorData.temperature)
            CurrentView("Current", sensorData.current)
            VoltageView("Voltage", sensorData.voltage)
            PressureView("Pressure", sensorData.pressure)

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { /* Implement generator control logic */ }) {
                Text(text = if (/* isGeneratorOn */ false) "Turn Off" else "Turn On")
            }
        }
    }
}

