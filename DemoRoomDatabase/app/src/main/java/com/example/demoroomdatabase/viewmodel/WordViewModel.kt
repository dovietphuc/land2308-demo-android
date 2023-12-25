package com.example.demoroomdatabase.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoroomdatabase.model.entity.Word
import com.example.demoroomdatabase.repository.WordRepository
import kotlinx.coroutines.launch

class WordViewModel(application: Application) : AndroidViewModel(application) {
    private val wordRepository = WordRepository(application.applicationContext)

    public fun getAllWords() = wordRepository.getAllWords()

    public fun insert(word: Word) {
        viewModelScope.launch {
            wordRepository.insert(word)
        }
    }

    public fun delete(word: Word) {
        viewModelScope.launch {
            wordRepository.delete(word)
        }
    }
}