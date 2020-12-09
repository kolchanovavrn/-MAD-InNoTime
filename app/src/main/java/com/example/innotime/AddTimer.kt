package com.example.innotime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.innotime.db.TimerDao
import com.example.innotime.db.TimerDbModel
import com.example.innotime.db.TimerRoomDatabase
import kotlinx.android.synthetic.main.activity_add_timer.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

class AddTimer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var db: TimerRoomDatabase?
        var timerDao: TimerDao?


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_timer)

        create.setOnClickListener {
            val label = label.text.toString()
            val time = time.text.toString()

            if (!label.isBlank() && !time.isBlank()) {
                db = TimerRoomDatabase.getTimerDataBase(context = this)
                timerDao = db?.timerDao()
                CoroutineScope(EmptyCoroutineContext).launch{
                    timerDao?.insertTimer(TimerDbModel(0, label, time.toLong(), "description"))
                }
                this.finish()
            } else {
                Toast.makeText(this, "Fill label and time", Toast.LENGTH_SHORT).show()
            }
        }


    }
}