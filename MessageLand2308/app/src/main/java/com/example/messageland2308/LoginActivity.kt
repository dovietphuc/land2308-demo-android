package com.example.messageland2308

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val edtEmail = findViewById<EditText>(R.id.edt_email)
        val edtPassword = findViewById<EditText>(R.id.edt_password)
        val btnLogin = findViewById<Button>(R.id.btn_login)

        btnLogin.setOnClickListener {
            login(edtEmail.text.toString(), edtPassword.text.toString())
        }
    }

    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {task ->
                if(task.isSuccessful) {
                    val firebaseUser = firebaseAuth.currentUser
                    if(firebaseUser != null && !firebaseUser.isEmailVerified) {
                        firebaseUser.sendEmailVerification()
                            .addOnCompleteListener { verifiedTask ->
                                Toast.makeText(this,
                                    "Check your email", Toast.LENGTH_SHORT).show()
                            }
                    } else if(firebaseUser != null && firebaseUser.isEmailVerified) {
                        Toast.makeText(this,
                            "LOGIN SUCCESS", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                } else {
                    Toast.makeText(this,
                        task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }
}