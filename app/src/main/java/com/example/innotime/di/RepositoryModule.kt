//package com.example.innotime.di
//
//import com.example.innotime.db.TimerDao
//import com.example.innotime.db.TimerRepository
//import dagger.Module
//import dagger.Provides
//import javax.inject.Singleton
//
//@Module
//class RepositoryModule {
//
//    @Singleton
//    @Provides
//    fun providesRepository(
//        timerDao: TimerDao
//    ): TimerRepository {
//        return TimerRepository(timerDao)
//    }
//}