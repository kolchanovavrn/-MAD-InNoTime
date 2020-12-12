package com.example.innotime.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.innotime.R
import com.example.innotime.SequentialTimerInfo
import com.example.innotime.TimerApplication
import com.example.innotime.db.TimerDao
import com.example.innotime.db.TimerDbModel
import com.example.innotime.db.TimerRoomDatabase
import com.google.gson.Gson
import kotlinx.android.synthetic.main.timer_info_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.EmptyCoroutineContext

class TimerInfoItemAdapter(
        val dao: TimerDao
    ) : RecyclerView.Adapter<TimerInfoItemAdapter.TimerInfoViewHolder>() {
    private var listOfTimers = listOf<TimerDbModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerInfoViewHolder {
        return TimerInfoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.timer_info_item, parent, false), dao
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

    class TimerInfoViewHolder(itemView: View, val dao: TimerDao) : RecyclerView.ViewHolder(itemView) {
        fun bindView(timerItem: TimerDbModel) {

//            itemView.breedTextView.text = animalItem.breed
//            itemView.animalNameTextView.text = animalItem.name
//            itemView.animalDescriptionTextView.text = animalItem.description
//            itemView.animalAgeTextView.text = animalItem.age
//            itemView.setOnClickListener {
//
//
//                val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(animalItem)
//                it.findNavController().navigate(action)
//            }


            val gson = Gson()
            val timer : SequentialTimerInfo = gson.fromJson(timerItem.data, SequentialTimerInfo::class.java)

            itemView.timerName.text = timer.name
            itemView.timerDesctiptionText.text = timer.desc
            itemView.timerTypeText.text = if (timer.timers.size > 1) "Sequential" else "Simple"

            itemView.timerDeleteButton.setOnClickListener{

                CoroutineScope(EmptyCoroutineContext).launch(Dispatchers.IO) {
                    dao.deleteTimer(timerItem)
                }
            }

            itemView.timerShareButton.setOnClickListener{
                // TODO : SHARE
            }
        }

    }
}