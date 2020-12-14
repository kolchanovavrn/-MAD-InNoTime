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
    var code = """
            {
                "id" : "TestTimer",
                "name" : "Wow, it works!",
                "desc" : "No one will read this",
                "startingTimer" : 0,
                "timers" : [
                    {
                        "id" : 0,
                        "name" : "first",
                        "durationInSecs" : 5,
                        "description" : "555",
                        "transitions" : [
                            {
                            "to" : 1,
                            "type" : 1,
                            "buttonText" : "This one should be invisible"
                            }
                        ]
                    },
                    {
                        "id" : 1,
                        "name" : "second",
                        "durationInSecs" : 7,
                        "description" : "777",
                        "transitions" : [
                            {
                            "to" : 0,
                            "type" : 0,
                            "buttonText" : "Wanna go back?"
                            },
                            {
                            "to" : 1,
                            "type" : 0,
                            "buttonText" : "Recursion?"
                            },
                            {
                            "to" : 2,
                            "type" : 0,
                            "buttonText" : "Go to next"
                            },
                            {
                            "to" : 3,
                            "type" : 0,
                            "buttonText" : "Skip next"
                            }
                        ]
                    },
                    {
                        "id" : 2,
                        "name" : "third",
                        "durationInSecs" : 10,
                        "description" : "1010",
                        "transitions" : [
                            {
                            "to" : 3,
                            "type" : 0,
                            "buttonText" : "Go?"
                            }
                        ]
                    },
                    {
                        "id" : 3,
                        "name" : "fourth (last)",
                        "durationInSecs" : 5,
                        "description" : "The last one",
                        "transitions" : []
                    }
                ]
            }
            
            
             
    """.trimIndent()

    code = """
            {
                "id" : "TestTimer2",
                "name" : "Wow, it works (2)!",
                "desc" : "No one will read this",
                "startingTimer" : 0,
                "timers" : [
                    {
                        "id" : 0,
                        "name" : "first",
                        "durationInSecs" : 5,
                        "description" : "555",
                        "transitions" : [
                            {
                            "to" : 1,
                            "type" : 0,
                            "buttonText" : "This one should be invisible"
                            }
                        ]
                    },
                    {
                        "id" : 1,
                        "name" : "second",
                        "durationInSecs" : 7,
                        "description" : "777",
                        "transitions" : [
                            {
                            "to" : 2,
                            "type" : 0,
                            "buttonText" : "Go to next"
                            }
                        ]
                    },
                    {
                        "id" : 2,
                        "name" : "third",
                        "durationInSecs" : 10,
                        "description" : "1010",
                        "transitions" : [
                            {
                            "to" : 3,
                            "type" : 0,
                            "buttonText" : "Go?"
                            }
                        ]
                    },
                    {
                        "id" : 3,
                        "name" : "fourth (last)",
                        "durationInSecs" : 5,
                        "description" : "The last one",
                        "transitions" : []
                    }
                ]
            }
            
            
             
    """.trimIndent()

    val gson = Gson()
    val res : SequentialTimerInfo = gson.fromJson(code, SequentialTimerInfo::class.java)
    res.validate()

    Log.e("DBG", res.toString())
    Log.e("DBG", res.validate().toString())

    return res
}