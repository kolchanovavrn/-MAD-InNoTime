package com.example.innotime.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

@Database(entities = [TimerDbModel::class], version = 3)
abstract class TimerRoomDatabase : RoomDatabase() {

    abstract fun timerDao(): TimerDao

    companion object {
        @Volatile
        private var INSTANCE: TimerRoomDatabase? = null

        fun getTimerDataBase(context: Context): TimerRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        TimerRoomDatabase::class.java,
                        "timers"
                )
                        .fallbackToDestructiveMigration()
                        .addCallback(TimerDatabaseCallback())
                        .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class TimerDatabaseCallback(
    ) : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
//                INSTANCE?.let { database ->
//                    CoroutineScope(EmptyCoroutineContext).launch(Dispatchers.IO) {
//                        populateDatabase(database.timerDao())
//                    }
//                }
        }
    }

    fun populateDatabase(timerDao: TimerDao) {
        timerDao.deleteAll()
    }
}