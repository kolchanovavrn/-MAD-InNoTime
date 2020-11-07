package com.example.innotime

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    enum class TimerState{
        Running,
        Paused,
        Stopped
    }
    private lateinit var timer: CountDownTimer
    private var timerState = TimerState.Stopped
    private var secondsRemaining: Long = 60

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        start.setOnClickListener{v ->
            startTimer()
            timerState =  TimerState.Running
            updateButtons()
        }

        pause.setOnClickListener { v ->
            timer.cancel()
            timerState = TimerState.Paused
            updateButtons()
        }

        stop.setOnClickListener { v ->
            timer.cancel()
            onTimerFinished()
        }
    }
    private fun updateButtons(){
        when (timerState) {
            TimerState.Running ->{
                start.isEnabled = false
                pause.isEnabled = true
                stop.isEnabled = true
            }
            TimerState.Stopped -> {
                start.isEnabled = true
                pause.isEnabled = false
                stop.isEnabled = false
            }
            TimerState.Paused -> {
                start.isEnabled = true
                pause.isEnabled = false
                stop.isEnabled = true
            }
        }
    }
    override fun onPause() {
        super.onPause()
        if (timerState == TimerState.Running){
            timer.cancel()
        }
    }
    @SuppressLint("SetTextI18n")
    private fun onTimerFinished(){
        timerState = TimerState.Stopped
        time.text = "Done!"
        updateButtons()
    }

    private fun startTimer(){
        timerState = TimerState.Running

        timer = object : CountDownTimer(secondsRemaining * 1000, 1000) {
            override fun onFinish() = onTimerFinished()

            override fun onTick(millisUntilFinished: Long) {
                secondsRemaining = millisUntilFinished / 1000
                time.text = ("$secondsRemaining")
            }
        }.start()
    }
}