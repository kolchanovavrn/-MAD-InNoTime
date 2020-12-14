package com.example.innotime.addTimer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.innotime.R
import kotlinx.android.synthetic.main.fragment_add_timer_main_page.*

class AddTimerMainPage : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_timer_main_page, container, false)
    }

    companion object {
        fun newInstance() = AddTimerMainPage()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        back.setOnClickListener {
            (requireActivity() as AddTimerActivity).finish()
        }

        nextStep.setOnClickListener {

            if (name.text.isEmpty() || description.text.isEmpty()) {
                Toast.makeText(activity, R.string.alert, Toast.LENGTH_LONG).show()
            } else {

                val editor = mSettings!!.edit()
                editor.putString(APP_PREFERENCES_NAME, name.text.toString())
                editor.putString(APP_PREFERENCES_DESCRIPTION, description.text.toString())
                editor.apply()

                if (type.isChecked) {
                    addSequentialTimer()
                } else {
                    addSimpleTimer()
                }
            }
        }
    }

    private fun addSimpleTimer() {
        (requireActivity() as AddTimerActivity).navigateToAddSimpleTimer()
    }

    private fun addSequentialTimer() {
        (requireActivity() as AddTimerActivity).navigateToAddSequentialTimer()
    }
}


