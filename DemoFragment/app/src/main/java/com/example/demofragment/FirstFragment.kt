package com.example.demofragment

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class FirstFragment : Fragment(R.layout.fragment_first) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val txtLabel = view.findViewById<TextView>(R.id.txt_label)
        txtLabel.setOnClickListener {
//            val args = Bundle()
//            args.putString("MESSAGE", "Hello message")
            parentFragmentManager.beginTransaction()
                .replace(R.id.host_container, SecondFragment("Hello args message"))
                .addToBackStack("firstFragment")
                .commit()
        }
    }
}