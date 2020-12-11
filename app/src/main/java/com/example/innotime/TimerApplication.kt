package com.example.innotime

import android.app.Application

class TimerApplication : Application() {

    val appComponent = DaggerApplicationComponent.create()
    var currentTimerState: RunningTimerState? = null;

    override fun onCreate() {
        super.onCreate()
        APPLICATION = this
    }

    companion object {
        lateinit var APPLICATION: Application
    }
}