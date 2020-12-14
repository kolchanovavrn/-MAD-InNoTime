package com.example.innotime.addTimer

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.innotime.R
import com.example.innotime.SequentialSingleTimerInfo
import com.example.innotime.SequentialTimerInfo
import com.example.innotime.SequentialTimerTransitionInfo
import com.example.innotime.adapters.SubTimersAdapter
import com.example.innotime.db.TimerDbModel
import com.example.innotime.db.TimerRoomDatabase
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_add_sequential_timer.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.NumberFormatException
import java.util.*
import kotlin.coroutines.EmptyCoroutineContext

class AddSequentialTimer : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_sequential_timer, container, false)
    }

    val adapter = SubTimersAdapter()

    companion object {
        fun newInstance() = AddSequentialTimer()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview);
        recyclerView.adapter = adapter

        cancel.setOnClickListener { goToMainPage() }

        newSubTimerDurationField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var working = s.toString()
                if (working.length == 2 && before === 0) {
                    if (working.toInt() > 23 || working.toInt() < 0) {
                        newSubTimerDurationField.setError("Enter a valid date: HH:MM:SS")
                    } else {
                        working += ":"
                        newSubTimerDurationField.setText(working)
                        newSubTimerDurationField.setSelection(working.length)
                    }
                } else if (working.length == 5 && before === 0) {
                    val minutes = working.substring(3, 5)
                    if (minutes.toInt() > 59) {
                        newSubTimerDurationField.setError("Enter a valid date: HH:MM:SS")
                    } else {
                        working += ":"
                        newSubTimerDurationField.setText(working)
                        newSubTimerDurationField.setSelection(working.length)
                    }
                } else if (working.length == 8 && before === 0) {
                    val seconds = working.substring(6, 8)
                    if (seconds.toInt() > 59) {
                        newSubTimerDurationField.setError("Enter a valid date: HH:MM:SS")
                    } else {
                        addNewSubTimerButton.isEnabled = true
                    }
                } else {
                    addNewSubTimerButton.isEnabled = false
                }
            }
        })

        create.setOnClickListener {
            val timersDao = TimerRoomDatabase.getTimerDataBase(activity!!.application).timerDao()

            val uuid = UUID.randomUUID().toString()
            val timersList = ArrayList<SequentialSingleTimerInfo>()

            var idx = 0
            adapter.listOfSubTimers.forEach {
                val newSingleTimer = SequentialSingleTimerInfo(
                    idx,
                    it.duration.toInt(),
                    it.subName,
                    "",
                    if (idx != adapter.listOfSubTimers.size - 1) arrayOf(
                        SequentialTimerTransitionInfo(idx + 1, 1, "Next")
                    ) else emptyArray()
                )
                timersList.add(newSingleTimer)
                idx++
            }
            val timersArray: Array<SequentialSingleTimerInfo> = timersList.toTypedArray()

            val newTimer = SequentialTimerInfo(
                uuid,
                mSettings?.getString(APP_PREFERENCES_NAME, "") ?: uuid,
                mSettings?.getString(APP_PREFERENCES_DESCRIPTION, "") ?: "",
                timersArray, 0
            )

            val gson = Gson()

            val timerJsonString = gson.toJson(newTimer).toString()

            CoroutineScope(EmptyCoroutineContext).launch(Dispatchers.IO) {
                timersDao.insertTimer(TimerDbModel(0, timerJsonString))
            }
            activity?.finish()
        }

        addNewSubTimerButton.setOnClickListener {
            val arr = newSubTimerDurationField.text.toString().split(':')
            val durationInSec = arr[0].toInt() * 60 * 60 + arr[1].toInt() * 60 + arr[2].toInt()
            if (arr.size != 3) {
                Toast.makeText(this.activity, R.string.duration_error, Toast.LENGTH_SHORT).show()
            } else {
                try {
                    val subTimerName = newSubTimerNameField.text.toString()
                    val subTimerDuration = durationInSec.toLong()

                    adapter.addNewSubTimer(
                        SubTimersAdapter.SubTimerData(
                            subTimerName,
                            subTimerDuration
                        )
                    )
                } catch (ex: NumberFormatException) {
                    Toast.makeText(this.activity, R.string.duration_error, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun goToMainPage() {
        (requireActivity() as AddTimerActivity).navigateToAddTimerMainPage()
    }
}