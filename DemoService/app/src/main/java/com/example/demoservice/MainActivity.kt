package com.example.demoservice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnPlay = findViewById<Button>(R.id.btn_play)
        val btnStop = findViewById<Button>(R.id.btn_stop)

        btnPlay.setOnClickListener {
            val intent = Intent(this, DemoService::class.java)
            intent.action = "ACTION_PLAY"
            startService(intent)
        }

        btnStop.setOnClickListener {
            val intent = Intent(this, DemoService::class.java)
            intent.action = "ACTION_STOP"
            startService(intent)
        }
    }
}