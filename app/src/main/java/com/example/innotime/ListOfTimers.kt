package com.example.innotime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.innotime.viewmodels.TimersViewModel
import javax.inject.Inject

class ListOfTimers : AppCompatActivity() {
    @Inject
    lateinit var timersViewModel: TimersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_timers)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = TimersListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        (application as TimerApplication).appComponent.inject(this)

        timersViewModel.allTimers.observe(this, Observer { timers ->
            timers?.let { adapter.setTimers(it) }
        })
    }
}