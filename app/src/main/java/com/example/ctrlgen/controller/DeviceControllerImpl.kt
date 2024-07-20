package com.example.ctrlgen.controller

import androidx.compose.runtime.mutableStateOf
import com.example.ctrlgen.model.DeviceType

class DeviceControllerImpl : DeviceController {
    private val deviceStates = mutableMapOf<DeviceType, Boolean>()

    init {
        // Initialize all devices as off
        DeviceType.values().forEach { deviceType ->
            deviceStates[deviceType] = false
        }
    }

    override fun turnOn(deviceType: DeviceType) {
        deviceStates[deviceType] = true
        // Add the logic to send a command to the device
        // For example, you might send a network request or interact with a service
    }

    override fun turnOff(deviceType: DeviceType) {
        deviceStates[deviceType] = false
        // Add the logic to send a command to the device
    }

    fun isDeviceOn(deviceType: DeviceType): Boolean {
        return deviceStates[deviceType] ?: false
    }
}
