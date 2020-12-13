package com.example.innotime

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.innotime.db.TimerDbModel
import com.example.innotime.db.TimerRoomDatabase
import com.example.innotime.api.Timer
import com.example.innotime.viewmodels.TimersViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.EmptyCoroutineContext

class MainActivity : AppCompatActivity() {

    enum class TimerState{
        Initial,
        Running,
        Paused,
        Stopped,
        Transition
    }

    @Inject
    lateinit var timersViewModel: TimersViewModel
    private lateinit var timer: CountDownTimer
    private var timerState = TimerState.Initial
    private var seconds: Long = 60
    private var secondsRemaining: Long = seconds
    private var pauseNext: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as TimerApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        list.setOnClickListener {

            if (timerState == TimerState.Running) {
                pauseTimer()
                updateButtons()
                Toast.makeText(this, "Your active timer has been paused", Toast.LENGTH_SHORT).show()
            }

            val intent = Intent()
            intent.setClassName(this@MainActivity, "com.example.innotime.ListOfTimers")
            startActivity(intent)
        }

        start.setOnClickListener { v ->
            when (timerState) {
                TimerState.Initial -> {
                    startTimer(seconds)
                }
                TimerState.Stopped -> {
                    updateFromRunningTimer(reset = true)
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

    private fun updateButtons() {
        when (timerState) {
            TimerState.Initial -> {
                start.isEnabled = true
                pause.isEnabled = false
                stop.isEnabled = false
            }
            TimerState.Running -> {
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

    override fun onResume() {
        super.onResume()

        if (timerState == TimerState.Stopped) {
            return
        }

        if (timerState == TimerState.Transition || timerState == TimerState.Initial) {
            updateFromRunningTimer()
        }
    }

    private fun updateFromRunningTimer(reset: Boolean = false) {
        val appContext = applicationContext as TimerApplication

        if (appContext.currentTimerState == null) {
            Toast.makeText(applicationContext, "No timer was set!", Toast.LENGTH_SHORT).show()
            finish()
        }

        if (reset) {
            appContext.currentTimerState!!.reset()
        }

        seconds = appContext.currentTimerState!!.remainingTime
        secondsRemaining = seconds

        updateButtons()
        label.setText(appContext.currentTimerState!!.timer.name + " : " + appContext.currentTimerState!!.getCurrentSingleTimer().name)


        if (pauseNext) {
            time.text = "$secondsRemaining"
            timerState = TimerState.Paused
            pauseNext = false

        } else {
            timerState = TimerState.Running
            startTimer(seconds)

        }


    }

    private fun pauseTimer() {
        if (timerState == TimerState.Running) {
            timer.cancel()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun onTimerFinished() {

        // if there are some transitions, go to TimerActions

        val appContext = applicationContext as TimerApplication

        if (appContext.currentTimerState!!.getTransitions().size != 0) {
            if (appContext.currentTimerState!!.getTransitions().size == 1 && appContext.currentTimerState!!.getTransitions()[0].type == 1) {
                val endTransition = appContext.currentTimerState!!.getTransitions()[0]
                appContext.currentTimerState!!.makeTransition(endTransition)

                updateFromRunningTimer()

//                startTimer(seconds)

            } else {
                val intent = Intent()
                intent.setClassName(this@MainActivity, "com.example.innotime.TimerActions")
                timerState = TimerState.Transition

                startActivity(intent)

//                this.finish()
            }
        } else {
            timerState = TimerState.Stopped
            time.text = "Done!"
            updateButtons()
        }
    }

    private fun startTimer(sec: Long) {

        timerState = TimerState.Running

        timer = object : CountDownTimer(sec * 1000, 1000) {
            override fun onFinish() = onTimerFinished()

            override fun onTick(millisUntilFinished: Long) {
                secondsRemaining = millisUntilFinished / 1000
                time.text = "$secondsRemaining"
            }
        }.start()
    }
}