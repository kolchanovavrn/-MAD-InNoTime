package com.example.innotime

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    enum class TimerState{
        Initial,
        Running,
        Paused,
        Stopped
    }
    private lateinit var timer: CountDownTimer
    private var timerState = TimerState.Stopped
    private var seconds: Long = 60
    private var secondsRemaining: Long = 60

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        timerState = TimerState.Initial
        updateButtons()
        start.setOnClickListener{v ->
            when(timerState) {
                TimerState.Initial ->{
                    startTimer(seconds)
                }
                TimerState.Stopped ->{
                    startTimer(seconds)
                }
                else -> startTimer(secondsRemaining)
            }
            updateButtons()
        }

        pause.setOnClickListener { v ->
            pauseTimer()
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
            TimerState.Initial ->{
                start.isEnabled = true
                pause.isEnabled = false
                stop.isEnabled = false
            }
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
    private fun pauseTimer() {
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

    private fun startTimer(sec : Long){
        timerState = TimerState.Running

        timer = object : CountDownTimer(sec * 1000, 1000) {
            override fun onFinish() = onTimerFinished()

            override fun onTick(millisUntilFinished: Long) {
                secondsRemaining = millisUntilFinished / 1000
                time.text = ("$secondsRemaining")
            }
        }.start()
    }
}