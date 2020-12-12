package com.example.innotime.api

import androidx.recyclerview.widget.DiffUtil
import com.example.innotime.SequentialTimerInfo
import com.google.gson.annotations.SerializedName

data class Timer (
    @SerializedName("json")
    val json: SequentialTimerInfo
)
