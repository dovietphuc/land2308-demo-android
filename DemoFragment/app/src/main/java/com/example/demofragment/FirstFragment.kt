package com.example.demofragment

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class FirstFragment : Fragment(R.layout.fragment_first) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { activity ->
            val mainViewModel =
                ViewModelProvider(activity)[MainActivityViewModel::class.java]
            val textView = view.findViewById<TextView>(R.id.txt_label)
            mainViewModel.count.observe(viewLifecycleOwner) {v ->
                textView.text = v.toString()
            }
        }
    }
}