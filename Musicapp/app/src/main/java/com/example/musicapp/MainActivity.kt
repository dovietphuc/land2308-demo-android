package com.example.musicapp

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import phucdv.android.musichelper.MediaHelper

class MainActivity : AppCompatActivity() {
    val musicController = MusicController(this).apply {
        setCompleteListener(MediaPlayer.OnCompletionListener {
            adapter.isPlaying = false
            adapter.notifyItemChanged(adapter.playingPos)
        })
    }

    lateinit var adapter: SongListAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = SongListAdapter()
        recyclerView = findViewById(R.id.rcv_list_song)
        recyclerView.adapter = adapter
        doRetrieveAllSong()

        adapter.itemClickListener = View.OnClickListener {v ->
            val pos = v.tag as Int
            adapter.notifyItemChanged(adapter.playingPos)
            adapter.playingPos = pos
            if(pos != musicController.currentSongIndex) {
                musicController.playSong(pos)
                adapter.isPlaying = true
            } else {
                if(musicController.isPlaying()) {
                    musicController.stopSong()
                    adapter.isPlaying = false
                } else {
                    musicController.continueSong()
                    adapter.isPlaying = true
                }
            }
            adapter.notifyItemChanged(adapter.playingPos)
        }
    }

    fun doRetrieveAllSong() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) ==
            PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 999)
        } else {
            MediaHelper.retrieveAllSong(this) { listSong ->
                adapter.data = listSong
                musicController.listSong = listSong
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) ==
            PackageManager.PERMISSION_GRANTED) {
            doRetrieveAllSong()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        musicController.release()
    }
}