package com.example.messageland2308

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val edtEmail = findViewById<EditText>(R.id.edt_email)
        val edtPassword = findViewById<EditText>(R.id.edt_password)
        val btnSignup = findViewById<Button>(R.id.btn_signup)
        val txtSignin = findViewById<TextView>(R.id.txt_login)

        btnSignup.setOnClickListener {
            signUp(edtEmail.text.toString(), edtPassword.text.toString())
        }

        txtSignin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    fun signUp(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    Toast.makeText(this,
                        "Successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this,
                        task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }
}