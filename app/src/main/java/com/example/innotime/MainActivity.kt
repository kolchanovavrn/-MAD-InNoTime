package com.example.innotime

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    enum class TimerState {
        Initial,
        Running,
        Paused,
        Stopped,
        Transition
    }

    private lateinit var timer: CountDownTimer
    private var timerState = TimerState.Initial
    private var seconds: Long = 60
    private var secondsRemaining: Long = seconds
    private var pauseNext: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // Service for background timers



        list.setOnClickListener {
            if (timerState == TimerState.Running) {
                pauseTimer()
                updateButtons()
                Toast.makeText(this, R.string.pause_active, Toast.LENGTH_SHORT).show()
            }

            // Saving remaning seconds
            val appContext = applicationContext as TimerApplication
            appContext.currentTimerState!!.remainingTime = secondsRemaining

            val intent = Intent()
            intent.setClassName(this@MainActivity, "com.example.innotime.ListOfTimers")
            startActivity(intent)
        }

        start.setOnClickListener { v ->
            when (timerState) {
                TimerState.Initial -> {
                    updateFromRunningTimer()
                    startTimer(seconds)
                }
                TimerState.Stopped -> {
                    updateFromRunningTimer(reset = true)
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

        updateFromRunningTimer()
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

        updateFromRunningTimer()

        if (timerState == TimerState.Transition) {
            startTimer(secondsRemaining)
            updateButtons()
        }
    }

    override fun onRestart() {
        super.onRestart()
        updateFromRunningTimer()
    }

    private fun updateFromRunningTimer(reset: Boolean = false) {
        val appContext = applicationContext as TimerApplication

        if (appContext.currentTimerState == null) {
            Toast.makeText(applicationContext, R.string.no_timer, Toast.LENGTH_SHORT).show()
            finish()
        }

        if (reset) {
            appContext.currentTimerState!!.reset()
        }

        if (appContext.currentTimerState!!.timer.timers.size == 1) {
            label.text =
                appContext.currentTimerState!!.timer.name
        } else {
            label.text =
                appContext.currentTimerState!!.timer.name + " : " + appContext.currentTimerState!!.getCurrentSingleTimer().name
        }


        if (appContext.currentTimerState!!.finished) {
            timerState = TimerState.Stopped
            time.text = resources.getString(R.string.done)
            progress.progress = 0
            updateButtons()

            return
        }

        seconds = appContext.currentTimerState!!.getCurrentSingleTimer().durationInSecs.toLong()
        secondsRemaining = appContext.currentTimerState!!.remainingTime

        progress.max = seconds.toInt()
        progress.progress = (
                seconds - secondsRemaining).toInt()

        time.text = "$secondsRemaining"
        updateButtons()



    }

    private fun pauseTimer() {
        if (timerState == TimerState.Running) {

            val appContext = applicationContext as TimerApplication
            appContext.currentTimerState!!.remainingTime = secondsRemaining
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

                if (pauseNext) {
                    timerState = TimerState.Paused
                    pauseNext = false

                } else {
                    timerState = TimerState.Running
                    startTimer(seconds)
                }

            } else {
                val intent = Intent()
                intent.setClassName(this@MainActivity, "com.example.innotime.TimerActions")
                timerState = TimerState.Transition

                startActivity(intent)
            }
        } else {
            appContext.currentTimerState!!.finished = true
            timerState = TimerState.Stopped
            time.text = resources.getString(R.string.done)
            updateButtons()
        }
    }

    private fun startTimer(sec: Long) {

        timerState = TimerState.Running

        progress.max = seconds.toInt()
        progress.progress = (seconds.toInt() - secondsRemaining.toInt())

        timer = object : CountDownTimer(sec * 1000, 1000) {
            override fun onFinish() = onTimerFinished()

            override fun onTick(millisUntilFinished: Long) {
                val hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 24
                val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60
                val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60
                time.text = "$hours:$minutes:$seconds"

                progress.incrementProgressBy(1)
            }
        }.start()
    }
}