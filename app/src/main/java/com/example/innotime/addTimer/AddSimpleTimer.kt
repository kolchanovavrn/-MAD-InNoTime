package com.example.innotime.addTimer

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.innotime.R
import com.example.innotime.SequentialSingleTimerInfo
import com.example.innotime.SequentialTimerInfo
import com.example.innotime.db.TimerDbModel
import com.example.innotime.db.TimerRoomDatabase
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_add_simple_timer.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.EmptyCoroutineContext


class AddSimpleTimer : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_simple_timer, container, false)
    }

    companion object {
        fun newInstance() = AddSimpleTimer()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        back.setOnClickListener { goToMainPage() }

        time.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var working = s.toString()
                if (working.length == 2 && before === 0) {
                    if (working.toInt() > 23 || working.toInt() < 0) {
                        time.setError("Enter a valid date: HH:MM:SS")
                    } else {
                        working += ":"
                        time.setText(working)
                        time.setSelection(working.length)
                    }
                } else if (working.length == 5 && before === 0) {
                    val minutes = working.substring(3, 5)
                    if (minutes.toInt() > 59) {
                        time.setError("Enter a valid date: HH:MM:SS")
                    } else {
                        working += ":"
                        time.setText(working)
                        time.setSelection(working.length)
                    }
                } else if (working.length == 8 && before === 0) {
                    val seconds = working.substring(6, 8)
                    if (seconds.toInt() > 59) {
                        time.setError("Enter a valid date: HH:MM:SS")
                    } else {
                        create.isEnabled = true
                    }
                } else {
                    create.isEnabled = false
                }
            }
        })

        create.setOnClickListener {
            val arr = time.text.toString().split(':')
            val durationInSec = arr[0].toInt() * 60 * 60 + arr[1].toInt() * 60 + arr[2].toInt()
            if (arr.size != 3) {
                Toast.makeText(this.activity, R.string.duration_error, Toast.LENGTH_SHORT).show()

            } else {
                // TODO: Add parser of HHMMSS TO SS
                try {

                    val timersDao =
                        TimerRoomDatabase.getTimerDataBase(activity!!.application).timerDao()

                    val uuid = UUID.randomUUID().toString()
                    val duration = durationInSec.toLong()

                    val gson = Gson()
                    val newDbModel =
                        TimerDbModel(
                            0,
                            gson.toJson(
                                SequentialTimerInfo(
                                    uuid,
                                    mSettings?.getString(APP_PREFERENCES_NAME, "") ?: uuid,
                                    mSettings?.getString(APP_PREFERENCES_DESCRIPTION, "") ?: "",
                                    arrayOf(
                                        SequentialSingleTimerInfo(
                                            0,
                                            duration.toInt(),
                                            "",
                                            "",
                                            emptyArray()
                                        )
                                    ),
                                    0
                                )
                            ).toString()
                        )
                    CoroutineScope(EmptyCoroutineContext).launch(Dispatchers.IO) {
                        timersDao.insertTimer(newDbModel)
                        activity?.finish()
                    }
                } catch (ex: NumberFormatException) {
                    Toast.makeText(this.activity, R.string.duration_error, Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
            }
        }
    }

    private fun goToMainPage() {
        (requireActivity() as AddTimerActivity).navigateToAddTimerMainPage()
    }
}