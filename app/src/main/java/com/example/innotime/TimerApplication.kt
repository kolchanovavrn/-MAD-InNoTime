package com.example.innotime

import android.app.Application
import com.example.innotime.DaggerApplicationComponent

class TimerApplication : Application() {

    val appComponent = DaggerApplicationComponent.create()

    override fun onCreate() {
        super.onCreate()
        APPLICATION = this
    }

    companion object {
        lateinit var APPLICATION: Application
    }
}