package com.example.innotime.addTimer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import java.lang.NumberFormatException
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

        create.setOnClickListener {
            println(time.text.toString().split(':'))
            // TODO: Add parser of HHMMSS TO SS
            try {

                val timersDao =
                    TimerRoomDatabase.getTimerDataBase(activity!!.application).timerDao()

                val uuid = UUID.randomUUID().toString()
                val duration = time.text.toString().toLong()

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
                Toast.makeText(this.activity, R.string.duration_error, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
    }

    private fun goToMainPage() {
        (requireActivity() as AddTimerActivity).navigateToAddTimerMainPage()
    }
}