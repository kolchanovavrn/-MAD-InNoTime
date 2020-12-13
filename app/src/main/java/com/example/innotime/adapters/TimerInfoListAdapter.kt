package com.example.innotime.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.innotime.R
import com.example.innotime.RunningTimerState
import com.example.innotime.SequentialTimerInfo
import com.example.innotime.api.Client
import com.example.innotime.db.TimerDao
import com.example.innotime.db.TimerDbModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.timer_info_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.EmptyCoroutineContext

class TimerInfoListAdapter(
        val dao: TimerDao,
        val application: TimerApplication,
        val activity: Activity
    ) : RecyclerView.Adapter<TimerInfoListAdapter.TimerInfoViewHolder>() {
    private var listOfTimers = listOf<TimerDbModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerInfoViewHolder {
        return TimerInfoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.timer_info_item, parent, false), dao, application, activity
        )
    }

    override fun getItemCount(): Int = listOfTimers.size

    override fun onBindViewHolder(holder: TimerInfoViewHolder, position: Int) {
        holder.bindView(listOfTimers[position])
    }

    fun setTimerInfoList(newListOfTimers: List<TimerDbModel>) {
        this.listOfTimers = newListOfTimers
        notifyDataSetChanged()
    }

    class TimerInfoViewHolder(itemView: View, val dao: TimerDao, val application: TimerApplication, val activity: Activity) : RecyclerView.ViewHolder(itemView) {
        fun bindView(timerItem: TimerDbModel) {

            val gson = Gson()
            val client: Client = Client("https://innotime.herokuapp.com/timers/")

            val timer: SequentialTimerInfo = gson.fromJson(timerItem.data, SequentialTimerInfo::class.java)

            itemView.timerName.text = timer.name
            itemView.timerDesctiptionText.text = timer.desc
            itemView.timerTypeText.text = if (timer.timers.size > 1) "Sequential" else "Simple"

            itemView.timerDeleteButton.setOnClickListener {

                CoroutineScope(EmptyCoroutineContext).launch(Dispatchers.IO) {
                    dao.deleteTimer(timerItem)
                }
            }

            itemView.timerStartButton.setOnClickListener{
                application.currentTimerState = RunningTimerState(timer)
                activity.finish()
            }

            itemView.timerShareButton.setOnClickListener {
                val answer = "{\"json\":" + timerItem.data + "}"
                client.timerService.postTimer(answer)
                        .enqueue(object : Callback<JSONObject> {
                            override fun onFailure(call: Call<JSONObject>, t: Throwable) {
                                Toast.makeText(itemView.context, "Cannot share this timer", Toast.LENGTH_SHORT).show()
                            }
                            override fun onResponse(call: Call<JSONObject>, response: Response<JSONObject>) {
                                Toast.makeText(itemView.context, response.body().toString(), Toast.LENGTH_SHORT).show()
                            }
                        })
            }
        }
    }
}