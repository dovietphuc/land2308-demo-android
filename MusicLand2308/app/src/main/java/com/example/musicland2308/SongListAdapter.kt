package com.example.musicland2308

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class SongListAdapter(var data: List<Song> = ArrayList(),
                      var listener: View.OnClickListener? = null)
    : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return object : ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.song_item, parent, false)){}
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val title = holder.itemView as TextView
        title.text = data[position].title
        holder.itemView.tag = data[position]
        holder.itemView.setOnClickListener(listener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

}