package com.example.musicland2308

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.ContentUris
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.provider.MediaStore
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Random


class MusicService : Service() {
    val MUSIC_CHANNEL_ID = "music_channel_id"

    interface OnSeekChangedListener {
        fun onSeekChanged(pos: Int)
    }

    enum class RepeatMode {
        OFF, ONE, ALL
    }

    private val onSeekChangeListeners = ArrayList<OnSeekChangedListener>()
    val listSong = MutableLiveData<List<Song>>(emptyList())
    private var currentSongIndex = -1
    lateinit var mediaPlayer: MediaPlayer
    private var isSuffer = false
    private var repeatMode = RepeatMode.OFF

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        // Init media player
        mediaPlayer = MediaPlayer()
        mediaPlayer.setOnPreparedListener {
            mediaPlayer.start()
            startForeground(111, createNotification())
        }
        mediaPlayer.setOnCompletionListener {
            if(repeatMode == RepeatMode.OFF) {
                currentSongIndex = -1
            } else if(repeatMode == RepeatMode.ONE) {
                playSongAtCurrentIndex()
            } else if(repeatMode == RepeatMode.ALL) {
                nextSong()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action) {
            "LOAD_DATA" -> /*Load data*/ loadAllSong()
            "ACTION_PLAY" -> /*Play*/ playSong(intent)
            "ACTION_CONTINUE" -> /*Continue*/ continueSong()
            "ACTION_PAUSE" -> /*pause*/ pauseSong()
            "ACTION_NEXT" -> /*next*/ nextSong()
            "ACTION_PREV" -> /*previous*/ previousSong()
            "ACTION_SEEK" -> /*seek*/ seekSong(intent)
            "ACTION_CLOSE" -> /*close*/ closeService()
            "ACTION_SUFFER" -> /*suffer playback*/ suffer()
            "ACTION_OFF_SUFFER" -> /*off suffer playback*/ offSuffer()
            "ACTION_REPEAT_ALL" -> /*repeat all*/ reapetAll()
            "ACTION_REPEAT_ONE" -> /*repeat one*/ reapetOne()
            "ACTION_OFF_ALL_REPEAT_MODE" -> /*off all repeat mode*/ offAllRepeatMode()
            else -> throw IllegalStateException("Unknown action")
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun offSuffer() {
        isSuffer = false
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
        repeatMode = RepeatMode.OFF
    }

    private fun reapetOne() {
        repeatMode = RepeatMode.ONE
    }

    private fun reapetAll() {
        repeatMode = RepeatMode.ALL
    }

    private fun suffer() {
        isSuffer = true
    }

    private fun closeService() {
        stopForeground(STOP_FOREGROUND_REMOVE)
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
        if(currentSongIndex != -1 && !isSuffer) {
            index = currentSongIndex - 1
            if(index < 0) {
                index = listSong.value!!.size - 1
            }
        } else if(isSuffer) {
            index = randomSongIndex()
        }
        currentSongIndex = index
        playSongAtCurrentIndex()
    }

    fun randomSongIndex() : Int {
        if(listSong.value!!.isEmpty()) {
            return -1
        } else {
            return Random().nextInt(listSong.value!!.size - 1)
        }
    }

    private fun nextSong() {
        var index = 0
        if(currentSongIndex != -1 && !isSuffer) {
            index = currentSongIndex + 1
            if(index >= listSong.value!!.size) {
                index = 0
            }
        } else if(isSuffer) {
            index = randomSongIndex()
        }
        currentSongIndex = index
        playSongAtCurrentIndex()
    }

    private fun pauseSong() {
        mediaPlayer.pause()
    }

    private fun playSongAtCurrentIndex() {
        mediaPlayer.reset()
        if(currentSongIndex == -1) return
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

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel.
            val name = "Music Channel"
            val descriptionText = "Description for channel"
            val importance = NotificationManager.IMPORTANCE_LOW
            val mChannel = NotificationChannel(MUSIC_CHANNEL_ID, name, importance)
            mChannel.description = descriptionText
            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    fun createNotification() : Notification {
        val mainIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent
            .getActivity(this, 101, mainIntent,
                PendingIntent.FLAG_IMMUTABLE)

        val prevIntent = Intent(this, MusicService::class.java)
        prevIntent.action = "ACTION_PREV"
        val prevPendingIntent = PendingIntent
            .getService(this, 102, prevIntent, PendingIntent.FLAG_IMMUTABLE)

        val controller = RemoteViews(packageName, R.layout.controller_notification)
        controller.setOnClickPendingIntent(R.id.btn_prev, prevPendingIntent)
        controller.setTextViewText(R.id.txt_title, listSong.value!![currentSongIndex].title)

        // @TODO: Hoàn thiện giao diện thanh thông báo và action cho nút play/pause/next

        val notification = NotificationCompat.Builder(this, MUSIC_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(controller)
            .setContentIntent(pendingIntent)
            .build()
        return notification
    }

    public inner class Binder : android.os.Binder() {
        fun getService() : MusicService {
            return this@MusicService
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return Binder()
    }

    suspend fun loadAllSongAsync() : List<Song> {
        return withContext(Dispatchers.IO) {
            val result = mutableListOf<Song>()

            val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            val projections = arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
//                MediaStore.Audio.Media.AUTHOR,
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
//                val authorIndex = c.getColumnIndexOrThrow(MediaStore.Audio.Media.AUTHOR)
                val albumIndex = c.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
                val durationIndex = c.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
                while(c.moveToNext()) {
                    val id = c.getInt(idIndex)
                    val title = c.getString(titleIndex)
                    val author = /*c.getString(authorIndex)*/""
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