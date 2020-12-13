package com.example.innotime.api

import retrofit2.Call
import retrofit2.http.GET

interface TimerService {
    @GET(".")
    fun getTimerByUrl():Call<Timer>
}