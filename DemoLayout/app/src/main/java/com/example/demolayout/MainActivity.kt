package com.example.demolayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.RelativeLayout
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

        txtValue = findViewById(R.id.txt_count)
        btnCount = findViewById(R.id.btn_count)
        btnToast = findViewById(R.id.btn_toast)

        btnCount.setOnClickListener {
            value++
            txtValue.text = value.toString()
        }

        btnToast.setOnClickListener {
            Toast.makeText(this, value.toString(), Toast.LENGTH_SHORT).show()
        }

    }
}