package com.example.demofragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.demofragment.databinding.FragmentSecondBinding

class SecondFragment() : Fragment(R.layout.fragment_second) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSecondBinding.bind(view)
        activity?.let { activity ->
            val mainViewModel =
                ViewModelProvider(activity)[MainActivityViewModel::class.java]
            binding.btnCount.setOnClickListener {
                mainViewModel.increment()
            }
        }
    }
}