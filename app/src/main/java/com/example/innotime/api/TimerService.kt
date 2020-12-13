package com.example.innotime.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TimerService {
    @GET("timer/{id}/?format=json")
    fun getTimerById(@Path("id") id:Int):Call<Timer>

    @GET("{url}")
    fun getTimerByUrl(@Path("url") url: String):Call<Timer>
}