package com.example.innotime

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.innotime.viewmodels.TimersViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    enum class TimerState{
        Initial,
        Running,
        Paused,
        Stopped
    }
    @Inject
    lateinit var timersViewModel: TimersViewModel
    private lateinit var timer: CountDownTimer
    private var timerState = TimerState.Stopped
    private var seconds : Long = 60
    private var secondsRemaining: Long = seconds

//  TODO: Add DB initialization in Main Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as TimerApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appContext = applicationContext as TimerApplication
        
        if (appContext.currentTimerState == null){
            Toast.makeText(applicationContext, "No timer was set!", Toast.LENGTH_SHORT).show()
            finish()
        }

        seconds = appContext.currentTimerState!!.remainingTime
        secondsRemaining = seconds

        startTimer(seconds)
        updateButtons()
        label.setText(appContext.currentTimerState!!.timer.name)


        timerState = TimerState.Initial
        updateButtons()

        delete.setOnClickListener{
            timersViewModel.getTimer.observe(this, Observer { list ->
// TODO: Cannot access database on the main thread since it may potentially lock the UI for a long period of time.
//                timerDao.deleteTimer(list)
                println(list)
            })
        }

        add.setOnClickListener{
            val intent = Intent()
            intent.setClassName(this, "com.example.innotime.AddTimer")
            startActivity(intent)
            this.finish()
        }

        list.setOnClickListener{
            val intent = Intent()
            intent.setClassName(this@MainActivity, "com.example.innotime.ListOfTimers")
            startActivity(intent)
            this.finish()
        }

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

        // if there are some transitions, go to TimerActions

        val appContext = applicationContext as TimerApplication

        if (appContext.currentTimerState!!.getTransitions().size != 0){
            if (appContext.currentTimerState!!.getEndTransition() != null){
                val endTransition = appContext.currentTimerState!!.getEndTransition()!!
                appContext.currentTimerState!!.makeTransition(endTransition)

                seconds = appContext.currentTimerState!!.remainingTime
                secondsRemaining = seconds

            } else {
                val intent = Intent()
                intent.setClassName(this@MainActivity, "com.example.innotime.TimerActions")
                startActivity(intent)
//                this.finish()
            }
        } else {
            timerState = TimerState.Stopped
            time.text = "Done!"
            updateButtons()
        }
    }

    private fun startTimer(sec : Long){
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