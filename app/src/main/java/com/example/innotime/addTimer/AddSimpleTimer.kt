package com.example.innotime.addTimer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.innotime.R
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
            Toast.makeText(activity, "Need to save somewhere", Toast.LENGTH_LONG).show()
        }
    }

    private fun goToMainPage() {
        (requireActivity() as AddTimerActivity).navigateToAddTimerMainPage()
    }
}