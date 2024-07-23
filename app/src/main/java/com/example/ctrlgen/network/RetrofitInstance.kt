package com.example.ctrlgen.network

import com.example.ctrlgen.model.ArduinoService
import com.example.ctrlgen.model.SensorData
import com.example.ctrlgen.model.SensorDataDeserializer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    private var retrofit: Retrofit? = null

    private fun getGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(SensorData::class.java, SensorDataDeserializer())
            .create()
    }

    fun getInstance(baseUrl: String): ArduinoService {
        if (retrofit == null || retrofit!!.baseUrl().toString() != baseUrl) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(40, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .build()
        }
        return retrofit!!.create(ArduinoService::class.java)
    }
}
