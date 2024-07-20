package com.example.ctrlgen.network

import com.example.ctrlgen.model.ArduinoService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private var retrofit: Retrofit? = null

    fun getInstance(baseUrl: String): ArduinoService {
        if (retrofit == null || retrofit!!.baseUrl().toString() != baseUrl) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!.create(ArduinoService::class.java)
    }
}
