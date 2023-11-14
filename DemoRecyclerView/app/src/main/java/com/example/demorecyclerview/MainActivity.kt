package com.example.demorecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.Callback
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Date
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = mutableListOf<Student>(
            Student("Phuc"),
            Student("AAAAA"),
            Student("BBBBBBBBBB"),
            Student("Cccccccc"),
            Student("dddddddddd"),
            Student("eeeeeeee"),
            Student("fffffffff"),
            Student("ggggggggg"),
            Student("hhhhhhhhhh"),
            Student("kkkkkkk"),
            Student("iiiiiiii"),
            Student("qqqqqqqqq"),
            Student("wwwwwwwwwww"),
            Student("eeeeeeeeeeee"),
            Student("rrrrrrrrrrrrr"),
            Student("ttttttttttt"),
            Student("yyyyyyyyyy"),
            Student("uuuuuuuu"),
            Student("iiiiiiiii"),
            Student("ooooooooooo")
        )

        val recyclerView = findViewById<RecyclerView>(R.id.rcv)
        val adapter = StringListAdapter()
        adapter.data = data
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val fab = findViewById<FloatingActionButton>(R.id.fab_add_item)
        fab.setOnClickListener {
            val name = Date().toString()
            val pos = Math.abs(Random(data.size).nextInt() % (data.size))
            data.add(pos, Student(name))
            adapter.notifyItemInserted(pos)
        }

        val itemTouchHelper =
            ItemTouchHelper(object : SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    val student = data.removeAt(viewHolder.adapterPosition)
                    if(student != null) {
                        data.add(target.adapterPosition, student)
                        adapter.notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)
                        return true
                    }
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    data.removeAt(viewHolder.adapterPosition)
                    adapter.notifyItemRemoved(viewHolder.adapterPosition)
                }

            })

        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}