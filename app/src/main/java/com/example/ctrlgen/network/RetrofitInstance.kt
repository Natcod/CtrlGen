package com.example.ctrlgen.network

import com.example.ctrlgen.model.ArduinoService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "http://your-arduino-ip/"

    val api: ArduinoService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ArduinoService::class.java)
    }
}
