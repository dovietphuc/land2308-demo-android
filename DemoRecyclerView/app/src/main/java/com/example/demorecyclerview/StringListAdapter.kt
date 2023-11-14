package com.example.demorecyclerview

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class StringListAdapter : RecyclerView.Adapter<StringListAdapter.StringViewHolder>() {
    var data: List<Student> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringViewHolder {
        Log.d("PhucDV", "onCreateViewHolder: ")
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.string_item, parent, false)
        val holder = StringViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
        Log.d("PhucDV", "onBindViewHolder: $holder $position")
        holder.txtName.text = data[position].name
        if(data[position].isFavorites) {
            holder.itemView.setBackgroundColor(Color.RED)
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class StringViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName = itemView.findViewById<TextView>(R.id.txt_name)

        init {
            itemView.setOnClickListener {
                data[adapterPosition].isFavorites = !data[adapterPosition].isFavorites
                notifyItemChanged(adapterPosition)
            }
        }
    }
}