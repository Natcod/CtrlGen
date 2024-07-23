package com.example.ctrlgen.screen

import android.content.Context
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
    val sensorData by viewModel.sensorData.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    var connectionStatus by remember { mutableStateOf("Disconnected") }
    var ipAddress by remember { mutableStateOf("") }
    var isValidIp by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

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
                        isValidIp = isValidIpAddress(it)
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
                                    saveIpAddress(context, ipAddress)
                                    viewModel.fetchSensorData("http://$ipAddress/")
                                    connectionStatus = if (sensorData.isPlaceholder) "Failed to Connect" else "Connected"
                                    if (connectionStatus == "Connected") {
                                        viewModel.setIpAddress(ipAddress)
                                        navController.navigate("home")
                                    }
                                }
                            } else {
                                isValidIp = false
                            }
                        },
                        enabled = ipAddress.isNotBlank()
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
                    Text(text = "Oil Level: ${sensorData.oilLevel.joinToString(", ")}")
                    Text(text = "Fuel Level: ${sensorData.fuelLevel.joinToString(", ")}")
                    Text(text = "Temperature: ${sensorData.temperature.joinToString(", ")}")
                    Text(text = "Current: ${sensorData.current}")
                    Text(text = "Voltage: ${sensorData.voltage}")
                    Text(text = "Pressure: ${sensorData.pressure}")
                }
            }
        }
    )
}

private fun saveIpAddress(context: Context, ipAddress: String) {
    val sharedPreferences = context.getSharedPreferences("ip_prefs", Context.MODE_PRIVATE)
    sharedPreferences.edit().putString("ip_address", ipAddress).apply()
}

@Preview(showBackground = true)
@Composable
fun PreviewConnectScreen() {
    ConnectScreen(navController = NavHostController(LocalContext.current))
}
