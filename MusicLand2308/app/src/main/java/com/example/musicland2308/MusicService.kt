package com.example.musicland2308

import android.app.Service
import android.content.ContentUris
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MusicService : Service() {
    interface OnSeekChangedListener {
        fun onSeekChanged(pos: Int)
    }

    private val onSeekChangeListeners = ArrayList<OnSeekChangedListener>()
    private val listSong = MutableLiveData<List<Song>>(emptyList())
    private var currentSongIndex = -1
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate() {
        super.onCreate()
        // Load data
        loadAllSong()
        // Init media player
        mediaPlayer = MediaPlayer()
        mediaPlayer.setOnPreparedListener {
            mediaPlayer.start()
        }
        mediaPlayer.setOnCompletionListener {
            currentSongIndex = -1
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action) {
            "ACTION_PLAY" -> /*Play*/ playSong(intent)
            "ACTION_CONTINUE" -> /*Continue*/ continueSong()
            "ACTION_PAUSE" -> /*pause*/ pauseSong()
            "ACTION_NEXT" -> /*next*/ nextSong()
            "ACTION_PREV" -> /*previous*/ previousSong()
            "ACTION_SEEK" -> /*seek*/ seekSong(intent)
            "ACTION_CLOSE" -> /*close*/ closeService()
            "ACTION_SUFFER" -> /*suffer playback*/ suffer()
            "ACTION_REPEAT_ALL" -> /*repeat all*/ reapetAll()
            "ACTION_REPEAT_ONE" -> /*repeat one*/ reapetOne()
            "ACTION_OFF_ALL_REPEAT_MODE" -> /*off all repeat mode*/ offAllRepeatMode()
            else -> throw IllegalStateException("Unknown action")
        }
        return super.onStartCommand(intent, flags, startId)
    }

    fun registerOnSeekChanged(onSeekChangedListener: OnSeekChangedListener) {
        onSeekChangeListeners.add(onSeekChangedListener)
    }

    fun unregisterOnSeekChanged(onSeekChangedListener: OnSeekChangedListener) {
        onSeekChangeListeners.remove(onSeekChangedListener)
    }

    private fun continueSong() {
        mediaPlayer.start()
    }

    private fun offAllRepeatMode() {
        TODO("Not yet implemented")
    }

    private fun reapetOne() {
        TODO("Not yet implemented")
    }

    private fun reapetAll() {
        TODO("Not yet implemented")
    }

    private fun suffer() {
        TODO("Not yet implemented")
    }

    private fun closeService() {
        TODO("Not yet implemented")
    }

    private fun seekSong(intent: Intent) {
        var pos = intent.getIntExtra("SEEK_POS", 0)
        pos = Math.min(pos, mediaPlayer.duration)
        mediaPlayer.seekTo(pos)
        dispatchSeekChanged()
    }

    fun dispatchSeekChanged() {
        onSeekChangeListeners.forEach {
            it.onSeekChanged(mediaPlayer.currentPosition)
        }
    }

    private fun previousSong() {
        var index = 0
        if(currentSongIndex != -1) {
            index = currentSongIndex - 1
            if(index < 0) {
                index = listSong.value!!.size - 1
            }
        }
        currentSongIndex = index
        playSongAtCurrentIndex()
    }

    private fun nextSong() {
        var index = 0
        if(currentSongIndex != -1) {
            index = currentSongIndex + 1
            if(index >= listSong.value!!.size) {
                index = 0
            }
        }
        currentSongIndex = index
        playSongAtCurrentIndex()
    }

    private fun pauseSong() {
        mediaPlayer.pause()
    }

    private fun playSongAtCurrentIndex() {
        mediaPlayer.reset()
        val songId = listSong.value!![currentSongIndex].id
        if(songId != -1) {
            val trackUri =
                ContentUris.withAppendedId(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, songId.toLong()
                )
            try {
                mediaPlayer.setDataSource(this, trackUri)
            } catch (e: Exception) {
                Log.e("MUSIC SERVICE", "Error starting data source", e)
            }
            mediaPlayer.prepareAsync()
        }
    }

    private fun playSong(intent: Intent) {
        val songId = intent.getIntExtra("SONG_ID", -1)
        if(songId > -1) {
            currentSongIndex = listSong.value?.indexOfFirst {
                if(it.id == songId) {
                    return@indexOfFirst true
                }
                return@indexOfFirst false
            }!!
            playSongAtCurrentIndex()
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    suspend fun loadAllSongAsync() : List<Song> {
        return withContext(Dispatchers.IO) {
            val result = mutableListOf<Song>()

            val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            val projections = arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.AUTHOR,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DURATION,
            )
            val clauses = "${MediaStore.Audio.Media.IS_MUSIC}=?"
            val clausesArgs = arrayOf("1")
            val order = "${MediaStore.Audio.Media.TITLE} ASC"
            val cursor = contentResolver.query(
                uri, projections, clauses, clausesArgs, order
            )

            cursor?.let { c ->
                val idIndex = c.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                val titleIndex = c.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
                val authorIndex = c.getColumnIndexOrThrow(MediaStore.Audio.Media.AUTHOR)
                val albumIndex = c.getColumnIndexOrThrow(MediaStore.Audio.Media.AUTHOR)
                val durationIndex = c.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
                while(c.moveToNext()) {
                    val id = c.getInt(idIndex)
                    val title = c.getString(titleIndex)
                    val author = c.getString(authorIndex)
                    val album = c.getString(albumIndex)
                    val duration = c.getLong(durationIndex)
                    val song = Song(id, title, author, album, duration)
                    result.add(song)
                }
            }

            cursor?.close()

            return@withContext result
        }
    }

    fun loadAllSong() {
        CoroutineScope(Dispatchers.Main).launch {
            val listAllSong = loadAllSongAsync()
            listSong.postValue(listAllSong)
        }
    }
}