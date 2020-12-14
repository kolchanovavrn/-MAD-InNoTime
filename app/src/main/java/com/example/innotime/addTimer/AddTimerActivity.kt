package com.example.innotime.addTimer

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.innotime.R

const val APP_PREFERENCES_NAME = "name"
const val APP_PREFERENCES_DESCRIPTION = "description"

var mSettings: SharedPreferences? = null

class AddTimerActivity : FragmentActivity() {
    private lateinit var addTimerMainPage: AddTimerMainPage
    private lateinit var addSequentialTimer: AddSequentialTimer
    private lateinit var addSimpleTimer: AddSimpleTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSettings = getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
        setContentView(R.layout.add_timer_activity)
        addTimerMainPage = AddTimerMainPage.newInstance()
        addSequentialTimer = AddSequentialTimer.newInstance()
        addSimpleTimer = AddSimpleTimer.newInstance()

        navigateToAddTimerMainPage()
    }

    fun navigateToAddTimerMainPage() {
        navigateTo(addTimerMainPage, true)
    }

    fun navigateToAddSimpleTimer() {
        navigateTo(addSimpleTimer, true)
    }

    fun navigateToAddSequentialTimer() {
        navigateTo(addSequentialTimer, true)
    }

    private fun navigateTo(fragment: Fragment, withBackStack: Boolean = false) {
        with(supportFragmentManager.beginTransaction())
        {
            replace(R.id.fragment_container, fragment)
            if (withBackStack) addToBackStack(null)
            commit()
        }
    }
}