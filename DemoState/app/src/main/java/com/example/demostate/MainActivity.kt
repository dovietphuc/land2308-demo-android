package com.example.demostate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("PhucDV", "onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d("PhucDV", "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d("PhucDV", "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.d("PhucDV", "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d("PhucDV", "onStop: ")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("PhucDV", "onRestart: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("PhucDV", "onDestroy: ")
    }
}