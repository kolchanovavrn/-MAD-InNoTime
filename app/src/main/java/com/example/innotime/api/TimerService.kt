package com.example.innotime.api

import com.example.innotime.SequentialTimerInfo
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TimerService {
    @GET(".")
    fun getTimerByUrl():Call<SequentialTimerInfo>

    @POST(".")
    fun postTimer(@Body json: String): Call<JSONObject>
}