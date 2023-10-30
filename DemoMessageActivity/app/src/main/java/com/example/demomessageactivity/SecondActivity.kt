package com.example.demomessageactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val txtMessage = findViewById<TextView>(R.id.txt_message)
        val edtMessage = findViewById<EditText>(R.id.edt_message)
        val btnReply = findViewById<Button>(R.id.btn_reply)

        btnReply.setOnClickListener {
            val message = edtMessage.text.toString().trim()
            if(message.isNotEmpty()) {
                val replyIntent = Intent()
                replyIntent.putExtra(MainActivity.EXTRA_MESSAGE, message)
                setResult(RESULT_OK, replyIntent)
                finish()
            }
        }

        if(intent != null) {
            val action = intent.action
            val message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE)
            if(action == MainActivity.ACTION_MESSAGE) {
                if(message != null) {
                    txtMessage.text = message
                }
            }
        }
    }
}