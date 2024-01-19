package com.example.messageland2308

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    val firebaseAuth = FirebaseAuth.getInstance()
    val firebaseUser = firebaseAuth.currentUser
    val firebaseStore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(firebaseUser == null || !firebaseUser.isEmailVerified) {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        val txtMessage = findViewById<TextView>(R.id.edt_message)
        val btnSend = findViewById<Button>(R.id.btn_send)

        btnSend.setOnClickListener {
            sendMessage(txtMessage.text.toString(), "akka@gmail.com")
        }

        loadMessageWithUser("akka@gmail.com")
    }

    fun sendMessage(message: String, toUserEmail: String) {
        val data = mapOf(Pair("message", message), Pair("time", System.currentTimeMillis()))
        firebaseStore.collection("Messages")
            .document(firebaseUser?.email!!)
            .collection("message_to")
            .document(toUserEmail)
            .collection("message_item")
            .document()
            .set(data)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    Toast.makeText(this,
                        "Send message successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, it.exception?.message,
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun loadMessageWithUser(email: String) {
        firebaseStore.collection("Messages")
            .document(email)
            .collection("message_to")
            .document(firebaseUser?.email!!)
            .collection("message_item")
            .addSnapshotListener { value, error ->
                value?.documentChanges?.forEach { document ->
                    val id = document.document.id
                    val message = document.document["message"]
                    val time = document.document["time"]
                    Log.d("PhucDV", "$id $message $time")
                }
            }
    }
}