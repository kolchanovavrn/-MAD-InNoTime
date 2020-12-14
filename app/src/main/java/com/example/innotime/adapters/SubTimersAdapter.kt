package com.example.innotime.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.innotime.R
import kotlinx.android.synthetic.main.sequential_sub_timer.view.*

class SubTimersAdapter : RecyclerView.Adapter<SubTimersAdapter.SubTimerViewHolder>() {
    var listOfSubTimers = ArrayList<SubTimerData>()
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