package com.example.demolayout

import android.graphics.BitmapFactory
import android.graphics.drawable.TransitionDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    var value = 0
    lateinit var txtValue: TextView
    lateinit var btnToast: Button
    lateinit var btnCount: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState != null) {
            value = savedInstanceState.getInt("VALUE")
        }

        txtValue = findViewById(R.id.txt_count)
        btnCount = findViewById(R.id.btn_count)
        btnToast = findViewById(R.id.btn_toast)

        txtValue.text = value.toString()

        btnCount.setOnClickListener {
            value++
            txtValue.text = value.toString()

            val background = txtValue.background as TransitionDrawable
            background.reverseTransition(300)
        }

        btnToast.setOnClickListener {
            Toast.makeText(this, value.toString(), Toast.LENGTH_SHORT).show()
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("VALUE", value)
    }
}