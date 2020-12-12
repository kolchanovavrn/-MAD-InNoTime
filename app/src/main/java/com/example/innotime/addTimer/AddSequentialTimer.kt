package com.example.innotime.addTimer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.innotime.R
import kotlinx.android.synthetic.main.fragment_add_sequential_timer.*

class AddSequentialTimer : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_sequential_timer, container, false)
    }

    companion object {
        fun newInstance() = AddSequentialTimer()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cancel.setOnClickListener { goToMainPage() }

        create.setOnClickListener {
            addSequentialSubTimer()
        }

    }

    private fun goToMainPage() {
        (requireActivity() as AddTimerActivity).navigateToAddTimerMainPage()
    }

    private fun addSequentialSubTimer() {
        (requireActivity() as AddTimerActivity).navigateToAddSequentialSubTimer()
    }
}