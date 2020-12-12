package com.example.innotime.addTimer

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.innotime.R

class AddTimerActivity : FragmentActivity() {
    private lateinit var addTimerMainPage: AddTimerMainPage
    private lateinit var addSequentialTimer: AddSequentialTimer
    private lateinit var addSequentialSubTimer: AddSequentialSubTimer
    private lateinit var addSimpleTimer: AddSimpleTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_timer_activity)
        addTimerMainPage = AddTimerMainPage.newInstance()
        addSequentialTimer = AddSequentialTimer.newInstance()
        addSequentialSubTimer = AddSequentialSubTimer.newInstance()
        addSimpleTimer = AddSimpleTimer.newInstance()

        navigateToAddTimerMainPage()
    }

    fun navigateToAddTimerMainPage(){
        navigateTo(addTimerMainPage, true)
    }

    fun navigateToAddSimpleTimer() {
        navigateTo(addSimpleTimer, true)
    }

    fun navigateToAddSequentialTimer() {
        navigateTo(addSequentialTimer, true)
    }

    fun navigateToAddSequentialSubTimer() {
        navigateTo(addSequentialSubTimer, true)
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