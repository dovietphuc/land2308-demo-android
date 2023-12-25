package com.example.demoroomdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.demoroomdatabase.model.entity.Word
import com.example.demoroomdatabase.viewmodel.WordViewModel

class MainActivity : AppCompatActivity() {
    lateinit var wordViewModel: WordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wordViewModel = ViewModelProvider(this)[WordViewModel::class.java]

        wordViewModel.getAllWords().observe(this) { listWords ->

        }

        wordViewModel.insert(Word("Alpha", "First letter"))
    }
}