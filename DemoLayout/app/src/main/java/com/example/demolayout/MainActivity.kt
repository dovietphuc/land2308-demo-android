package com.example.demolayout

import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.graphics.drawable.TransitionDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    lateinit var preferences: SharedPreferences
    lateinit var viewModel: MainActivityViewModel
    lateinit var txtValue: TextView
    lateinit var btnToast: Button
    lateinit var btnCount: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel =
            ViewModelProvider(this)[MainActivityViewModel::class.java]

        txtValue = findViewById(R.id.txt_count)
        btnCount = findViewById(R.id.btn_count)
        btnToast = findViewById(R.id.btn_toast)

        viewModel.value.observe(this) { v ->
            txtValue.text = v.toString()
        }

        btnCount.setOnClickListener {
            viewModel.increment()
        }

        btnToast.setOnClickListener {
            Toast.makeText(this,
                viewModel.value.toString(), Toast.LENGTH_SHORT).show()
        }

        preferences = getPreferences(MODE_PRIVATE)
        viewModel.value.postValue(
            preferences.getInt("VALUE", 0)
        )
    }

    override fun onPause() {
        super.onPause()
        preferences
            .edit()
            .putInt("VALUE", viewModel.value.value!!)
            .apply()
    }
}