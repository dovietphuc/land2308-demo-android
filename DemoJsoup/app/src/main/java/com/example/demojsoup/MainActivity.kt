package com.example.demojsoup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.helper.HttpConnection
import org.jsoup.select.Elements

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CoroutineScope(Dispatchers.Main).launch {
            val elements = loadItem()
            elements.forEach { item ->
                val titleElement = item.select("a")
                val labelElement = item.select(".label")
                Log.d("PhucDV", "onCreate: ${titleElement.text()}")
            }
        }
    }

    suspend fun loadItem() : Elements {
        return withContext(Dispatchers.IO) {
            val url = "https://voz.vn/s/chia-se-kien-thuc.104/"
            val document = Jsoup.connect(url).get()
            val selector = ".structItemContainer .structItem .structItem-title"
            document.select(selector)
        }
    }
}