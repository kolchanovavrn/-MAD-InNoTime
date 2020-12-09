package com.example.innotime.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [TimerDbModel::class], version = 2)
abstract class TimerRoomDatabase : RoomDatabase() {

    abstract fun timerDao(): TimerDao

    companion object {
        @Volatile
        private var INSTANCE: TimerRoomDatabase? = null

        fun getTimerDataBase(context: Context, scope: CoroutineScope): TimerRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        TimerRoomDatabase::class.java,
                        "timers"
                )
                        .fallbackToDestructiveMigration()
                        .addCallback(TimerDatabaseCallback(scope))
                        .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class TimerDatabaseCallback(
            private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
//                INSTANCE?.let { database ->
//                    scope.launch(Dispatchers.IO) {
//                        populateDatabase(database.timerDao())
//                    }
//                }
        }
    }

    fun populateDatabase(timerDao: TimerDao) {
        timerDao.deleteAll()
    }
}