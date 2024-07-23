package com.example.ctrlgen

import android.app.Application
import android.content.Context
import java.io.File

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = getSharedPreferences("ip_prefs", Context.MODE_PRIVATE)
        val ipAddress = sharedPreferences.getString("ip_address", "placeholder.com") ?: "placeholder.com"
        updateNetworkSecurityConfig(ipAddress)
    }

    private fun updateNetworkSecurityConfig(ipAddress: String) {
        val configContent = """
            <?xml version="1.0" encoding="utf-8"?>
            <network-security-config>
                <domain-config cleartextTrafficPermitted="true">
                    <domain includeSubdomains="true">$ipAddress</domain>
                </domain-config>
            </network-security-config>
        """.trimIndent()
        val file = File(filesDir, "network_security_config.xml")
        file.writeText(configContent)
    }
}
