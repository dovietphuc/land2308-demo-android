package com.example.demoroomdatabase.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.demoroomdatabase.model.WordDatabase
import com.example.demoroomdatabase.model.entity.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class WordRepository(val context: Context) {
    private val db = WordDatabase.getInstance(context)
    private val wordDAO = db.wordDAO()
    private val allWords = wordDAO.getAllWords()

    public fun getAllWords() = allWords

    public suspend fun insert(word: Word) {
        withContext(Dispatchers.IO) {
            wordDAO.insert(word)
        }
    }

    public suspend fun delete(word: Word) {
        withContext(Dispatchers.IO) {
            wordDAO.delete(word)
        }
    }
}