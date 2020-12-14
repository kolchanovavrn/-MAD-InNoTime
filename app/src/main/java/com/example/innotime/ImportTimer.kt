package com.example.innotime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.innotime.api.Client
import com.example.innotime.db.TimerDbModel
import com.example.innotime.db.TimerRoomDatabase
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_import_timer.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import kotlin.coroutines.EmptyCoroutineContext

class ImportTimer : AppCompatActivity() {
    lateinit var client: Client

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_import_timer)

        back.setOnClickListener {
            this.finish()
        }

        create.setOnClickListener {
            val timersDao = TimerRoomDatabase.getTimerDataBase(this.application).timerDao()
            val url = url.text.toString()
            client = Client(url)
            client.timerService.getTimerByUrl()
                .enqueue(object : Callback<SequentialTimerInfo> {
                    override fun onFailure(call: Call<SequentialTimerInfo>, t: Throwable) {
                        Toast.makeText(this@ImportTimer, R.string.url_error, Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onResponse(
                        call: Call<SequentialTimerInfo>,
                        response: Response<SequentialTimerInfo>
                    ) {
                        if (response.body() === null) {
                            Toast.makeText(
                                this@ImportTimer,
                                R.string.url_error,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            try {
                                if (response.body()!!.validate()) {
                                    val gson = Gson()
                                    val newDbModel = TimerDbModel(
                                        0,
                                        gson.toJson(
                                            response.body(),
                                            SequentialTimerInfo::class.java
                                        ).toString()
                                    )
                                    CoroutineScope(EmptyCoroutineContext).launch(Dispatchers.IO) {
                                        timersDao.insertTimer(newDbModel)
                                        this@ImportTimer.finish()
                                    }
                                } else {
                                    Toast.makeText(
                                        this@ImportTimer,
                                        R.string.url_error,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } catch (ex: Exception) {
                                Toast.makeText(
                                    this@ImportTimer,
                                    R.string.url_error,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                })
        }
    }
}