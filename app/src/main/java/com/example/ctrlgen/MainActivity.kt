package com.example.ctrlgen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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
fun MyApp(viewModel: MainViewModel = viewModel()) {
    val sensorData by remember(viewModel) { viewModel.sensorData }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
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
        } else {
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
