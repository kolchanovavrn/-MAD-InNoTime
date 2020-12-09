package com.example.innotime.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TimerDbModel::class], version = 2)
abstract class TimerRoomDatabase : RoomDatabase() {

    abstract fun timerDao(): TimerDao

    companion object {
        var INSTANCE: TimerRoomDatabase? = null

        fun getTimerDataBase(context: Context): TimerRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(TimerRoomDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        TimerRoomDatabase::class.java,
                        "timers"
                    )
                    .fallbackToDestructiveMigration()
                    .build()
                }
            }
            return INSTANCE
        }
    }
}