package com.example.land2308githubuser

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.land2308githubuser.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainActivityViewModel
    lateinit var binding: ActivityMainBinding
    val adapter: UserAdapter = UserAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel =
            ViewModelProvider(this)[MainActivityViewModel::class.java]

        binding.rcvUser.adapter = adapter

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.allUsers()
                .flowOn(Dispatchers.IO)
                .collectLatest {
                    it?.let {
                        adapter.data = it
                        adapter.notifyDataSetChanged()
                    }
                }
        }
    }
}