package com.example.demothread

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Random

class MainActivity : AppCompatActivity() {
    lateinit var root: View
    val handler = object : Handler(Looper.myLooper()!!) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val action = msg.what
            when (action) {
                1 -> root.setBackgroundColor(Color.RED)
                2 -> root.setBackgroundColor(Color.GREEN)
                3 -> root.setBackgroundColor(Color.BLUE)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        root = findViewById(R.id.root)
        val button = findViewById<Button>(R.id.btn_start)
        button.setOnClickListener {
            doSomethings()
        }
    }

    val colorFlow = flow<Int> {
        val color = longTask()
        emit(color)
    }

    suspend fun longTask(): Int {
        return withContext(Dispatchers.IO) {
            Thread.sleep(5000)
            val color = Random().nextInt(3) + 1
            when (color) {
                1 -> Color.RED
                2 -> Color.GREEN
                3 -> Color.BLUE
                else -> Color.BLACK
            }
        }
    }

    fun doSomethings() {
        lifecycleScope.launch {
            colorFlow.collect {color ->
                root.setBackgroundColor(color)
            }
        }

//        Thread {
//            Thread.sleep(5000)
//            val what = Random().nextInt(3) + 1
//            val msg = handler.obtainMessage(what)
//            handler.sendMessageDelayed(msg, 5000)
//            handler.post {
//                val color = Random().nextInt(3) + 1
//                when(color) {
//                    1 -> root.setBackgroundColor(Color.RED)
//                    2 -> root.setBackgroundColor(Color.GREEN)
//                    3 -> root.setBackgroundColor(Color.BLUE)
//                }
//            }
//        }.start()
    }
}