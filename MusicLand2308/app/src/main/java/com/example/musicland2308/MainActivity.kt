package com.example.musicland2308

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

val ATLEAST_TIRAMISU = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: SongListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rcv_song)
        adapter = SongListAdapter()
        recyclerView.adapter = adapter

        requestNeedsPermission()
//        loadAllSong()
    }

    fun checkNeedsPermission(): Boolean {
        val result: Int
        if (!ATLEAST_TIRAMISU) {
            result = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
        } else {
            result = checkSelfPermission(Manifest.permission.READ_MEDIA_AUDIO)
        }
        return result == PackageManager.PERMISSION_GRANTED
    }

    fun requestNeedsPermission() {
        if(!checkNeedsPermission()) {
            val permission: String
            if(!ATLEAST_TIRAMISU) {
                permission = Manifest.permission.READ_EXTERNAL_STORAGE
            } else {
                permission = Manifest.permission.READ_MEDIA_AUDIO
            }
            requestPermissions(
                arrayOf(permission),
                999
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 999) {
            if(!checkNeedsPermission()) {
                finish()
            }
        }
    }
}