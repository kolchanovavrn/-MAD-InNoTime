package com.example.innotime.db

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class TimerRepository(private val timerDao: TimerDao) {

    val allTimers: LiveData<List<TimerDbModel>> = timerDao.getTimers()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(timerDbModel: TimerDbModel) {
        timerDao.insertTimer(timerDbModel)
    }
}
