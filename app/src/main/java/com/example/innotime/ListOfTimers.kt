package com.example.innotime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.innotime.adapters.TimerInfoItemAdapter
import com.example.innotime.db.TimerDao
import com.example.innotime.db.TimerRoomDatabase
import com.example.innotime.viewmodels.TimersViewModel
import javax.inject.Inject

class ListOfTimers : AppCompatActivity() {
    @Inject
    lateinit var timersViewModel: TimersViewModel



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val timersDao = TimerRoomDatabase.getTimerDataBase(application).timerDao()

        setContentView(R.layout.activity_list_of_timers)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = TimerInfoItemAdapter(timersDao)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        timersDao.getTimers().observe(this, Observer {timers ->
            adapter.setTimerInfoList(timers)
        })

//        (application as TimerApplication).appComponent.inject(this)
//
//        timersViewModel.allTimers.observe(this, Observer { timers ->
//            timers?.let { adapter.setTimers(it) }
//        })
    }
}