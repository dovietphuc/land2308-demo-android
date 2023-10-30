package com.example.demopiegraph

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pieGraphView = findViewById<PieGraphView>(R.id.pie_graph)
        pieGraphView.data =
            listOf(PieGraphView.PieData("Test", 299f, Color.RED),
                PieGraphView.PieData("Test", 455f, Color.BLUE),
                        PieGraphView.PieData("Test", 125f, Color.GREEN))
    }
}