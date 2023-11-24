package com.example.demofragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.host_container, FirstFragment())
                .commit()

            supportFragmentManager.beginTransaction()
                .replace(R.id.host_container_2, SecondFragment())
                .commit()
        }
    }
}