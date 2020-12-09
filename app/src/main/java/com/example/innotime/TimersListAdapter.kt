package com.example.innotime

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.innotime.db.TimerDbModel

class TimersListAdapter internal constructor(
        context: Context
) : RecyclerView.Adapter<TimersListAdapter.TimersViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var timers = emptyList<TimerDbModel>() // Cached copy of words

    inner class TimersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timersItemView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimersViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return TimersViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TimersViewHolder, position: Int) {
        val current = timers[position]
        holder.timersItemView.text = current.name
    }

    internal fun setTimers(words: List<TimerDbModel>) {
        this.timers = words
        notifyDataSetChanged()
    }

    override fun getItemCount() = timers.size
}


