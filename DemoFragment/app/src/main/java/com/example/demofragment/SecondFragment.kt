package com.example.demofragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class SecondFragment(val message: String) : Fragment(R.layout.fragment_second) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val txtMessage = view.findViewById<TextView>(R.id.txt_message)
        txtMessage.text = message
//        arguments?.let {
//            txtMessage.text = it.getString("MESSAGE")
//        }
    }
}