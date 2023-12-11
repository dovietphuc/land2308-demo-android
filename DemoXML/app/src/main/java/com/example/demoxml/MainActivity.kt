package com.example.demoxml

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CoroutineScope(Dispatchers.Main).launch {
            val listItems = loadItems()
            for (item in listItems) {
                Log.d("PhucDV", "${item.title} - ${item.description}")
            }
        }
    }

    suspend fun loadItems() : List<Item> {
        return withContext(Dispatchers.IO) {
            val url = "https://stackoverflow.com/feeds/tag?tagnames=android&sort=newest"
            val input = downloadUrl(url)
            input?.let {
                return@withContext parser(input)
            }
            return@withContext ArrayList<Item>()
        }
    }

    @Throws(IOException::class)
    private fun downloadUrl(urlString: String): InputStream? {
        val url = URL(urlString)
        val conn = url.openConnection() as HttpURLConnection
        conn.readTimeout = 10000
        conn.connectTimeout = 15000
        conn.requestMethod = "GET"
        conn.doInput = true
        // Starts the query
        conn.connect()
        return conn.inputStream
    }

}