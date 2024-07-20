package com.example.ctrlgen.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ctrlgen.controller.DeviceControllerImpl
import com.example.ctrlgen.model.DeviceType

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val deviceController = remember { DeviceControllerImpl() }
    var isGeneratorOn by remember { mutableStateOf(deviceController.isDeviceOn(DeviceType.GENERATOR)) }
    var isFanOn by remember { mutableStateOf(deviceController.isDeviceOn(DeviceType.FAN)) }
    var isOilPumpOn by remember { mutableStateOf(deviceController.isDeviceOn(DeviceType.OIL_PUMP)) }
    var isFuelPumpOn by remember { mutableStateOf(deviceController.isDeviceOn(DeviceType.FUEL_PUMP)) }

    Column {
        // Add a new row for additional switches with a background color
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            color = Color(0xFFCFD1D5)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SwitchWithLabel(
                    label = "Fan",
                    checked = isFanOn,
                    onCheckedChange = { checked ->
                        isFanOn = checked
                        if (checked) deviceController.turnOn(DeviceType.FAN) else deviceController.turnOff(DeviceType.FAN)
                    }
                )
                SwitchWithLabel(
                    label = "Oil Pump",
                    checked = isOilPumpOn,
                    onCheckedChange = { checked ->
                        isOilPumpOn = checked
                        if (checked) deviceController.turnOn(DeviceType.OIL_PUMP) else deviceController.turnOff(DeviceType.OIL_PUMP)
                    }
                )
                SwitchWithLabel(
                    label = "Fuel Pump",
                    checked = isFuelPumpOn,
                    onCheckedChange = { checked ->
                        isFuelPumpOn = checked
                        if (checked) deviceController.turnOn(DeviceType.FUEL_PUMP) else deviceController.turnOff(DeviceType.FUEL_PUMP)
                    }
                )
            }
        }

        BottomNavigation(
            backgroundColor = Color(0xFFCFD1D5),
            contentColor = Color.White
        ) {
            BottomNavigationItem(
                icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                label = { Text("Home", color = Color.Black) },
                selected = false,
                onClick = {
                    navController.navigate("home")
                }
            )
            BottomNavigationItem(
                icon = {
                    Switch(
                        checked = isGeneratorOn,
                        onCheckedChange = { checked ->
                            isGeneratorOn = checked
                            if (checked) deviceController.turnOn(DeviceType.GENERATOR) else deviceController.turnOff(DeviceType.GENERATOR)
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = Color.Green,
                            uncheckedThumbColor = Color.White,
                            uncheckedTrackColor = Color.Red
                        )
                    )
                },
                label = { Text("Control", color = Color.Black) },
                selected = false,
                onClick = { /* Do nothing on click */ }
            )
            BottomNavigationItem(
                icon = {
                    Icon(Icons.Default.ArrowForward, contentDescription = "Arrow Forward")
                },
                label = { Text("Connect", color = Color.Black) },
                selected = false,
                onClick = {
                    navController.navigate("connect")
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBottomNavigationBar() {
    val navController = rememberNavController()
    BottomNavigationBar(navController = navController)
}


