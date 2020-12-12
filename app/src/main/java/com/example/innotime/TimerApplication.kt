package com.example.innotime

import android.app.Application
import android.util.Log
import com.google.gson.Gson

class TimerApplication : Application() {

    val appComponent = DaggerApplicationComponent.create()
    var currentTimerState: RunningTimerState? = RunningTimerState(getTestTimer());

    override fun onCreate() {
        super.onCreate()
        APPLICATION = this
    }

    companion object {
        lateinit var APPLICATION: Application
    }
}

fun getTestTimer() : SequentialTimerInfo {
    val code = """
        {
            "id" : "TestTimer",
            "name" : "Wow, it works!",
            "desc" : "No one will read this",
            "startingTimer" : 0,
            "timers" : [
                {
                    "id" : 0,
                    "durationInSecs" : 5,
                    "description" : "555",
                    "transitions" : [
                        {
                        "transitionID" : 0,
                        "to" : 1,
                        "type" : 0,
                        "buttonText" : "This one should be invisible"
                        }
                    ],
                    "endTransition" : 0
                },
                {
                    "id" : 1,
                    "durationInSecs" : 7,
                    "description" : "777",
                    "transitions" : [
                        {
                        "transitionID" : 0,
                        "to" : 0,
                        "type" : 0,
                        "buttonText" : "Wanna go back?"
                        },
                        {
                        "transitionID" : 1,
                        "to" : 1,
                        "type" : 0,
                        "buttonText" : "Recursion?"
                        },
                        {
                        "transitionID" : 2,
                        "to" : 2,
                        "type" : 0,
                        "buttonText" : "Go to next"
                        },
                        {
                        "transitionID" : 3,
                        "to" : 3,
                        "type" : 0,
                        "buttonText" : "Skip next"
                        }
                    ],
                    "endTransition" : null
                },
                {
                    "id" : 2,
                    "durationInSecs" : 10,
                    "description" : "1010",
                    "transitions" : [
                        {
                        "transitionID" : 0,
                        "to" : 3,
                        "type" : 0,
                        "buttonText" : "Go?"
                        }
                    ],
                    "endTransition" : null
                },
                {
                    "id" : 3,
                    "durationInSecs" : 5,
                    "description" : "The last one",
                    "transitions" : [],
                    "endTransition" : null
                }
            ]
        }
            
            
             
    """.trimIndent()

    val gson = Gson()
    val res : SequentialTimerInfo = gson.fromJson(code, SequentialTimerInfo::class.java)

    Log.e("DBG", res.toString())
    Log.e("DBG", res.validate().toString())

    return res
}