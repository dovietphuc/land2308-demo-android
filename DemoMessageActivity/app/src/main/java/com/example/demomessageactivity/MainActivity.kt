package com.example.demomessageactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    companion object {
        public const val ACTION_MESSAGE = "com.example.demomessageactivity.action.MESSAGE"
        public const val EXTRA_MESSAGE = "com.example.demomessageactivity.extra.MESSAGE"
    }

    lateinit var txtMessage : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtMessage = findViewById(R.id.txt_message)
        val edtMessage = findViewById<EditText>(R.id.edt_message)
        val btnSend = findViewById<Button>(R.id.btn_send)
        btnSend.setOnClickListener {
            val message = edtMessage.text.toString().trim()
            if (message.isNotEmpty()) {
                val intent = Intent(this, SecondActivity::class.java)
                intent.action = ACTION_MESSAGE
                intent.putExtra(EXTRA_MESSAGE, message)
                startActivityForResult(intent, 999)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == 999) {
                if (data != null) {
                    val message = data.getStringExtra(EXTRA_MESSAGE)
                    if (message != null) {
                        txtMessage.text = message
                    }
                }
            }
        }
    }
}