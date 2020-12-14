package com.example.innotime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_timer_actions.*

class TimerActions : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer_actions)

        timerActionButtons.removeAllViews()

        val appContext = applicationContext as TimerApplication

        appContext.currentTimerState?.getTransitions()?.forEach { trans ->
            val newButton = Button(this)
            newButton.setText(trans.buttonText)
            newButton.setOnClickListener {
                appContext.currentTimerState!!.makeTransition(trans)
                finish()
            }
            timerActionButtons.addView(newButton)
        }

        actionsFinishButton.setOnClickListener {
            appContext.currentTimerState!!.finished = true
            finish()
        }

    }
}