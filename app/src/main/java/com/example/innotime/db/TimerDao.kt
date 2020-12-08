package com.example.innotime.db

import androidx.lifecycle.LiveData;
import androidx.room.*

@Dao
interface TimerDao {

    @Query("SELECT * from timers_table ORDER BY id ASC")
    fun getTimers(): List<TimerDbModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTimer(timerDbModel: TimerDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(timerDbModel: List<TimerDbModel>)

    @Delete
    fun deleteTimer(timerDbModel: TimerDbModel)
}