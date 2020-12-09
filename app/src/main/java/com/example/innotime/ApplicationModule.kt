package com.example.innotime

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    @Provides
    fun provideApplication() = TimerApplication.APPLICATION

    @Provides
    fun provideContext(): Context = TimerApplication.APPLICATION

}