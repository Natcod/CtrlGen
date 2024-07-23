package com.example.ctrlgen.controller
import com.example.ctrlgen.model.DeviceType

interface DeviceController {
    fun turnOn(deviceType: DeviceType)
    fun turnOff(deviceType: DeviceType)
}
