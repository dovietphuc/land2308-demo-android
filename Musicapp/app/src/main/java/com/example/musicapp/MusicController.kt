package com.example.musicapp

import android.content.ContentUris
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.provider.MediaStore
import android.util.Log
import phucdv.android.musichelper.Song

class MusicController(val context: Context) {
    private val mediaPlayer = MediaPlayer().apply {
        setOnPreparedListener {
            start()
        }
    }

    fun setCompleteListener(completeListener: OnCompletionListener) {
        mediaPlayer.setOnCompletionListener(completeListener)
    }

    var listSong = emptyList<Song>()
    var currentSongIndex = -1

    fun playSong(index: Int) {
        currentSongIndex = index
        mediaPlayer.reset()
        val trackUri =
            ContentUris.withAppendedId(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, listSong[index].id)
        try {
            mediaPlayer.setDataSource(context, trackUri)
        } catch (e: Exception) {
            Log.e("MUSIC SERVICE", "Error starting data source", e)
        }
        mediaPlayer.prepareAsync()
    }

    fun next() {
        if(currentSongIndex == listSong.size) {
            currentSongIndex = -1
        }
        playSong(currentSongIndex + 1)
    }

    fun prev(){
        if(currentSongIndex == 0) {
            currentSongIndex = listSong.size
        }
        playSong(currentSongIndex - 1)
    }

    fun continueSong() {
        mediaPlayer.start()
    }

    fun stopSong() {
        mediaPlayer.pause()
    }

    fun isPlaying() = mediaPlayer.isPlaying

    fun release(){
        mediaPlayer.reset()
        mediaPlayer.release()
    }
}