package com.example.musicapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import phucdv.android.musichelper.Song

class SongListAdapter() : Adapter<SongListAdapter.SongViewHolder>() {
    var itemClickListener: OnClickListener? = null

    var data: List<Song> = emptyList()

    var playingPos = -1
    var isPlaying = false

    inner class SongViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtTitle = view as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        return SongViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.song_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.txtTitle.tag = position
        holder.txtTitle.text = data[position].title + " - " + data[position].formatTimes
        holder.txtTitle.setOnClickListener(itemClickListener)

        if(position == playingPos) {
            if(isPlaying) {
                holder.txtTitle.setBackgroundColor(Color.GREEN)
            } else {
                holder.txtTitle.setBackgroundColor(Color.YELLOW)
            }
        } else {
            holder.txtTitle.background = null
        }
    }
}