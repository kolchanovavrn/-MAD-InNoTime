package com.example.innotime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.innotime.adapters.TimerInfoListAdapter
import com.example.innotime.db.TimerRoomDatabase
import kotlinx.android.synthetic.main.activity_list_of_timers.*

class ListOfTimers : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val timersDao = TimerRoomDatabase.getTimerDataBase(application).timerDao()

        setContentView(R.layout.activity_list_of_timers)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = TimerInfoListAdapter(timersDao, application!! as TimerApplication, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        timersDao.getTimers().observe(this, Observer { timers ->
            adapter.setTimerInfoList(timers)
        })

        create.setOnClickListener {
            val intent = Intent()
            intent.setClassName(this, "com.example.innotime.addTimer.AddTimerActivity")
            startActivity(intent)
        }

        importTimer.setOnClickListener {
            val intent = Intent()
            intent.setClassName(this, "com.example.innotime.ImportTimer")
            startActivity(intent)
        }
    }
}