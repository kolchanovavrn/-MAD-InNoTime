package com.example.innotime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.innotime.api.Client
import com.example.innotime.api.Timer
import kotlinx.android.synthetic.main.activity_import_timer.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImportTimer : AppCompatActivity() {
    lateinit var client: Client

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_import_timer)

        back.setOnClickListener {
            this.finish()
        }

        create.setOnClickListener {
            val url = url.text.toString()
            client = Client()
//            client.timerService.getTimerById(id)
//                .enqueue(object : Callback<Timer> {
//                    override fun onFailure(call: Call<Timer>, t: Throwable) {
//                        Toast.makeText(this@ImportTimer, "Error!", Toast.LENGTH_SHORT).show()
//                    }
//
//                    override fun onResponse(call: Call<Timer>, response: Response<Timer>) {
//                        if (response.body() === null) {
//                            Toast.makeText(
//                                this@ImportTimer,
//                                "No such timer",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        } else {
//                            println(response.body())
//                        }
//                    }
//                })
            client.timerService.getTimerByUrl(url)
                .enqueue(object : Callback<Timer> {
                    override fun onFailure(call: Call<Timer>, t: Throwable) {
                        Toast.makeText(this@ImportTimer, "Error!", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<Timer>, response: Response<Timer>) {
                        if (response.body() === null) {
                            Toast.makeText(
                                this@ImportTimer,
                                "No such timer",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            println(response.body())
                        }
                    }
                })
        }

    }
}