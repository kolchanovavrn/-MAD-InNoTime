package com.example.innotime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.innotime.db.TimerDao
import com.example.innotime.db.TimerDbModel
import com.example.innotime.db.TimerRepository
import com.example.innotime.db.TimerRoomDatabase
import com.example.innotime.viewmodels.TimersViewModel
import kotlinx.android.synthetic.main.activity_add_timer.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.EmptyCoroutineContext

class AddTimer : AppCompatActivity() {
//    @Inject lateinit var timersViewModel: TimersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_timer)

        (application as TimerApplication).appComponent.inject(this)

        create.setOnClickListener {
            val replyIntent = Intent()
            val label = label.text.toString()
            val time = time.text.toString()

            if (!label.isBlank() && !time.isBlank()) {
                CoroutineScope(EmptyCoroutineContext).launch{
                    replyIntent.putExtra(TITLE, label)
                    replyIntent.putExtra(TIME, time)

//                    timersViewModel.insert(TimerDbModel(0, label, time.toLong(), "description"))

                }
                finish()
            } else {
                Toast.makeText(this, "Fill label and time", Toast.LENGTH_SHORT).show()
            }
        }
    }
    companion object {
        const val TITLE = " "
        const val TIME = " "
    }
}