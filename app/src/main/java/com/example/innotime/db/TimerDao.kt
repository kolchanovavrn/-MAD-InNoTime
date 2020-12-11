package com.example.innotime.db

import androidx.lifecycle.LiveData
import androidx.room.*
import java.sql.Time

@Dao
interface TimerDao {

    @Query("SELECT * from timers_table ORDER BY id ASC")
    fun getTimers(): LiveData<List<TimerDbModel>>

    @Query ("Select * from timers_table WHERE id = :id")
    fun getTimer(id: Int): LiveData<TimerDbModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTimer(timerDbModel: TimerDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(timerDbModel: List<TimerDbModel>)

    @Delete
    fun deleteTimer(timerDbModel: TimerDbModel)

    @Query("DELETE from timers_table")
    fun deleteAll();
}