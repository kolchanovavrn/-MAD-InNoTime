package com.example.innotime.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.innotime.db.TimerDbModel
import com.example.innotime.db.TimerRepository
import com.example.innotime.db.TimerRoomDatabase
import javax.inject.Inject

class TimersViewModel
@Inject constructor(application: Application) : AndroidViewModel(application) {

    private val repository: TimerRepository

    val allTimers: LiveData<List<TimerDbModel>>

    val getTimer: LiveData<TimerDbModel>


    init {
        val timersDao = TimerRoomDatabase.getTimerDataBase(application).timerDao()
        repository = TimerRepository(timersDao)
        allTimers = repository.allTimers
        getTimer = repository.getTimer
    }
}