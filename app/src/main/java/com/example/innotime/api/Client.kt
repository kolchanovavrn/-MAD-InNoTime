package com.example.innotime.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class Client(url: String) {
    private val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder().apply {
        connectTimeout(60, TimeUnit.SECONDS)
        readTimeout(60, TimeUnit.SECONDS)
        writeTimeout(60, TimeUnit.SECONDS)
    }
    private val retrofit = Retrofit.Builder().apply {
        baseUrl(url)
        client(okHttpBuilder.build())
        addConverterFactory(ScalarsConverterFactory.create())
        addConverterFactory(GsonConverterFactory.create())
    }.build()

    var timerService: TimerService

    init {
        timerService = retrofit.create(TimerService::class.java)
    }
}
