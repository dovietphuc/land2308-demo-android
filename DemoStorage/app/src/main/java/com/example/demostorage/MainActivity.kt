package com.example.demostorage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        readAsset()

        writeInternal()
        readInternal()

        writeTempFile()

        writeExternal()
    }

    fun readAsset(){
        val input = assets.open("data.txt")
        val str = StringBuilder()
        while(input.available() > 0) {
            str.append(input.read().toChar())
        }
        input.close()
        Log.d("PhucDV", "readAsset: $str")
    }

    fun writeInternal() {
        val content = "This is internal file content"
        val fileOutput = openFileOutput("internal_file.txt", MODE_PRIVATE)
        fileOutput.write(content.toByteArray())
    }

    fun readInternal(){
        val input = openFileInput("internal_file.txt")
        val str = StringBuilder()
        while(input.available() > 0) {
            str.append(input.read().toChar())
        }
        input.close()
        Log.d("PhucDV", "readInternal: $str")
    }

    fun writeTempFile() {
        File.createTempFile("temp.txt", null, cacheDir)
    }

    fun writeExternal() {
        val content = "This is external file content"
        val file = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
                                                    "external_file.txt")
        file.createNewFile()
    }
}