package com.example.innotime

import dagger.Component

@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    fun inject(activity: AddTimer)

    fun inject(activity: ListOfTimers)
}