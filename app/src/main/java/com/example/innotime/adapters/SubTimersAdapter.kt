package com.example.innotime.adapters

import android.app.Activity
import android.text.Editable
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.innotime.R
import com.example.innotime.RunningTimerState
import com.example.innotime.SequentialTimerInfo
import com.example.innotime.TimerApplication
import com.example.innotime.db.TimerDao
import com.example.innotime.db.TimerDbModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.sequential_sub_timer.view.*
import kotlinx.android.synthetic.main.timer_info_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

class SubTimersAdapter : RecyclerView.Adapter<SubTimersAdapter.SubTimerViewHolder>() {
    public var listOfSubTimers = ArrayList<SubTimerData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubTimerViewHolder {
        return SubTimerViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.sequential_sub_timer, parent, false)
        )
    }

    override fun getItemCount(): Int = listOfSubTimers.size

    override fun onBindViewHolder(holder: SubTimerViewHolder, position: Int) {
        holder.bindView(listOfSubTimers[position], position, this)
    }

//    fun updateSubTimer(newData: SubTimerData, position: Int) {
//        this.listOfSubTimers[position] = newData
////        notifyDataSetChanged()
//    }

    fun removeSubTimer(position: Int) {
        this.listOfSubTimers.removeAt(position)
        notifyDataSetChanged()
    }

    fun addNewSubTimer(newData: SubTimerData) {
        this.listOfSubTimers.add(newData)
        notifyDataSetChanged()
    }

    class SubTimerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(timerItem: SubTimerData, position: Int, adapter: SubTimersAdapter) {

            itemView.subTimerNameField.setText(timerItem.subName)
            itemView.subTimerDurationField.setText(timerItem.duration.toString())

            itemView.deleteSubTimerButton.setOnClickListener {
                adapter.removeSubTimer(position)
            }

        }

    }

    data class SubTimerData(
        val subName: String,
        val duration: Long,
    )
}