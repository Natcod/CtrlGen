package com.example.ctrlgen.controller

import android.util.Log
import com.example.ctrlgen.model.DeviceType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class DeviceControllerImpl : DeviceController {
    private val deviceStates = mutableMapOf<DeviceType, Boolean>()
    private val serverUrl = "http://192.168.250.7/device/control"

    init {
        // Initialize all devices as off
        DeviceType.values().forEach { deviceType ->
            deviceStates[deviceType] = false
        }
    }

    override fun turnOn(deviceType: DeviceType) {
        deviceStates[deviceType] = true
        sendCommandToDevice(deviceType, true)
    }

    override fun turnOff(deviceType: DeviceType) {
        deviceStates[deviceType] = false
        sendCommandToDevice(deviceType, false)
    }

    fun isDeviceOn(deviceType: DeviceType): Boolean {
        return deviceStates[deviceType] ?: false
    }

    private fun sendCommandToDevice(deviceType: DeviceType, turnOn: Boolean) {
        // Make the network request in a coroutine
        kotlinx.coroutines.GlobalScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val url = URL(serverUrl)
                    val connection = url.openConnection() as HttpURLConnection
                    connection.requestMethod = "POST"
                    connection.setRequestProperty("Content-Type", "application/json; utf-8")
                    connection.doOutput = true

                    val jsonInputString = JSONObject().apply {
                        put("deviceType", deviceType.name)
                        put("turnOn", turnOn)
                    }.toString()

                    OutputStreamWriter(connection.outputStream).use { writer ->
                        writer.write(jsonInputString)
                        writer.flush()
                    }

                    val responseCode = connection.responseCode
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        Log.d("DeviceController", "Command sent successfully")
                    } else {
                        Log.e("DeviceController", "Failed to send command: $responseCode")
                    }

                } catch (e: Exception) {
                    Log.e("DeviceController", "Exception in sendCommandToDevice: ${e.message}")
                }
            }
        }
    }
}
