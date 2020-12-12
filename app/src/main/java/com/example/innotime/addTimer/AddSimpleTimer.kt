package com.example.innotime.addTimer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.innotime.R
import com.example.innotime.db.TimerDbModel
import com.example.innotime.db.TimerRoomDatabase
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_add_simple_timer.*

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

//        GET NAME && DESCRIPTION
//        mSettings?.getString(APP_PREFERENCES_NAME, "")
//        mSettings?.getString(APP_PREFERENCES_DESCRIPTION, "")
        create.setOnClickListener {
//            Toast.makeText(activity, "Need to save somewhere", Toast.LENGTH_LONG).show()
            val timersDao = TimerRoomDatabase.getTimerDataBase(activity!!.application).timerDao()

            val gson = Gson()
//            gson.toJson()

//            timersDao.insertTimer(TimerDbModel(0, ))

        }
    }

    private fun goToMainPage() {
        (requireActivity() as AddTimerActivity).navigateToAddTimerMainPage()
    }
}