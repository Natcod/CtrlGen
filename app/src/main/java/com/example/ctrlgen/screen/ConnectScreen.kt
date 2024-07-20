package com.example.ctrlgen.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ctrlgen.controller.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun ConnectScreen(navController: NavHostController, viewModel: MainViewModel = viewModel()) {
    var connectionStatus by remember { mutableStateOf("Disconnected") }
    var ipAddress by remember { mutableStateOf("") }
    var isValidIp by remember { mutableStateOf(true) }
    val sensorData by viewModel.sensorData
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Connect to Arduino") })
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Status: $connectionStatus")
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = ipAddress,
                    onValueChange = {
                        ipAddress = it
                        isValidIp = it.isNotBlank() // Validate IP address field is not blank
                    },
                    label = { Text("Arduino IP Address") },
                    isError = !isValidIp,
                    modifier = Modifier.fillMaxWidth()
                )
                if (!isValidIp) {
                    Text(
                        text = "IP Address is required",
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (ipAddress.isNotBlank()) {
                            scope.launch {
                                viewModel.fetchSensorData("http://$ipAddress/")
                                connectionStatus = if (sensorData.isPlaceholder) "Failed to Connect" else "Connected"
                            }
                        } else {
                            isValidIp = false // Show error message if IP address is blank
                        }
                    },
                    enabled = ipAddress.isNotBlank() // Disable button if IP address is blank
                ) {
                    Text(text = "Connect")
                }

                if (!sensorData.isPlaceholder) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Oil Level: ${sensorData.oilLevel}")
                    Text(text = "Fuel Level: ${sensorData.fuelLevel}")
                    Text(text = "Temperature: ${sensorData.temperature}")
                    Text(text = "Current: ${sensorData.current}")
                    Text(text = "Voltage: ${sensorData.voltage}")
                    Text(text = "Pressure: ${sensorData.pressure}")
                }
            }
        }
    )
}

// Uncomment this part if you want to preview your composable in Android Studio
/*
@Preview(showBackground = true)
@Composable
fun PreviewConnectScreen() {
    ConnectScreen(navController = rememberNavController())
}
*/
