package com.example.ctrlgen.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ctrlgen.controller.MainViewModel
import kotlinx.coroutines.launch
import java.util.regex.Pattern

val IP_ADDRESS_PATTERN = Pattern.compile(
    "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"
)

fun isValidIpAddress(ip: String): Boolean {
    return IP_ADDRESS_PATTERN.matcher(ip).matches()
}

@Composable
fun ConnectScreen(navController: NavHostController, viewModel: MainViewModel = viewModel()) {
    var connectionStatus by remember { mutableStateOf("Disconnected") }
    var ipAddress by remember { mutableStateOf("") }
    var isValidIp by remember { mutableStateOf(true) }
    val sensorData by viewModel.sensorData
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage
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
                        isValidIp = isValidIpAddress(it) // Validate IP address format
                    },
                    label = { Text("Arduino IP Address") },
                    isError = !isValidIp,
                    modifier = Modifier.fillMaxWidth()
                )
                if (!isValidIp) {
                    Text(
                        text = "Invalid IP Address",
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    Button(
                        onClick = {
                            if (isValidIp) {
                                scope.launch {
                                    viewModel.fetchSensorData("http://$ipAddress/")
                                    connectionStatus = if (sensorData.isPlaceholder) "Failed to Connect" else "Connected"
                                }
                            } else {
                                isValidIp = false // Show error message if IP address is invalid
                            }
                        },
                        enabled = ipAddress.isNotBlank() // Disable button if IP address is blank
                    ) {
                        Text(text = "Connect")
                    }
                }

                if (errorMessage != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = errorMessage ?: "",
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.align(Alignment.Start)
                    )
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

@Preview(showBackground = true)
@Composable
fun PreviewConnectScreen() {
    ConnectScreen(navController = NavHostController(LocalContext.current))
}
