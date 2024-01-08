package com.example.demoservice

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class DemoService : Service() {

    val CHANNEL_ID = "demo_channel_id"

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        Log.d("PhucDV", "onCreate: ")
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel.
            val name = "Demo Channel"
            val descriptionText = "Description for channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.description = descriptionText
            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("PhucDV", "onStartCommand: $startId ${intent?.action}")
        intent?.let {
            if(it?.action.equals("ACTION_PLAY")) {
                startForeground(999, createNotification(startId))
            } else if(it?.action.equals("ACTION_STOP")) {
                stopForeground(STOP_FOREGROUND_DETACH)
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    @TargetApi(Build.VERSION_CODES.O)
    fun createNotification(data: Int) : Notification {
        val mainIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent
            .getActivity(this, 101, mainIntent, 0)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("This is title")
            .setContentText("This is content text $data")
            .setContentIntent(pendingIntent)
            .build()
        return notification
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("PhucDV", "onDestroy: ")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}