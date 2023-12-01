package com.example.demodialog

import android.app.ProgressDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup.LayoutParams
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress

class MainActivity : AppCompatActivity() {

    val items = arrayOf("1", "2", "3", "4", "5")
    val selected = booleanArrayOf(false, false, true, false, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnShow = findViewById<Button>(R.id.btn_show)
        btnShow.setOnClickListener {
//            AlertDialog.Builder(this)
//                .setTitle("This is title")
//                .setMessage("This is message")
//                .setPositiveButton("OK") { dialog, which ->
//                    Toast.makeText(this, "OK CLICKED", Toast.LENGTH_SHORT).show()
//                }
//                .setNegativeButton("Cancel") { dialog, which ->
//                    Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
//                }
//                .setNeutralButton("RESET") { dialog, which ->
//                    Toast.makeText(this,  "Reset", Toast.LENGTH_SHORT).show()
//                }
//                .create()
//                .show()

//            AlertDialog.Builder(this)
//                .setTitle("Multiple selections")
//                .setMultiChoiceItems(items, selected) { dialog, which, select ->
//                    selected[which] = select
//                }
//                .setPositiveButton("DONE") {dialog, which ->}
//                .create()
//                .show()

//            val linearLayout = LinearLayout(this)
//            val linearParams = LayoutParams(LayoutParams.MATCH_PARENT,
//                                                                LayoutParams.WRAP_CONTENT)
//            linearLayout.orientation = LinearLayout.VERTICAL
//            linearLayout.layoutParams = linearParams
//
//            val userName = EditText(this)
//            val userNameParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
//                                                                    LayoutParams.WRAP_CONTENT)
//            userName.layoutParams = userNameParams
//            userName.hint = "Username"
//
//            linearLayout.addView(userName)
//
//            AlertDialog.Builder(this)
//                .setTitle("Custom dialog")
//                .setView(linearLayout)
//                .create().show()

//            val dialog = ProgressDialog(this)
//            dialog.setTitle("Downloading")
//            dialog.setMessage("Loading...")
//            dialog.show()
//            Thread() {
//                download()
//                dialog.dismiss()
//            }.start()

            bindProgressButton(btnShow)
            btnShow.attachTextChangeAnimator()
            btnShow.showProgress {
                buttonTextRes = R.string.loading
                progressColor = Color.WHITE
            }
            Thread {
                download()
                runOnUiThread {
                    btnShow.hideProgress("Done")
                }
            }.start()
        }
    }

    fun download(){
        Thread.sleep(5000)
    }
}