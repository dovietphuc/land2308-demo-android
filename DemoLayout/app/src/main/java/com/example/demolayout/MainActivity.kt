package com.example.demolayout

import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.graphics.drawable.TransitionDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.demolayout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainActivityViewModel
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(this)[MainActivityViewModel::class.java]
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setContentView(binding.root)

        binding.txtCount.apply {
            val url = "https://s3.cloud.cmctelecom.vn/tinhte2/2020/03/4936913_cute-cats-wallpapers-8____by____twalls.jpg"
            Glide.with(this)
                .load(url)
                .circleCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(this)
        }

//        binding.btnCount.setOnClickListener {
//            viewModel.increment()
//        }
//
//        binding.btnToast.setOnClickListener {
//            Toast.makeText(this,
//                viewModel.value.toString(), Toast.LENGTH_SHORT).show()
//        }

//        preferences = getPreferences(MODE_PRIVATE)
//        viewModel.value.postValue(
//            preferences.getString("VALUE_STRING", "0")
//        )
    }

    override fun onPause() {
        super.onPause()
//        preferences
//            .edit()
//            .putString("VALUE_STRING", viewModel.value.value!!)
//            .apply()
    }
}