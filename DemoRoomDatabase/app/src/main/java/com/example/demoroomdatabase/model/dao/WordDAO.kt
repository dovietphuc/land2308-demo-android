package com.example.demoroomdatabase.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.ABORT
import androidx.room.Query
import com.example.demoroomdatabase.model.entity.Word

@Dao
interface WordDAO {

    @Query("SELECT * FROM word")
    fun getAllWords(): LiveData<List<Word>>

    @Insert(onConflict = ABORT)
    suspend fun insert(word: Word)

    @Delete
    suspend fun delete(word: Word)

    @Query("SELECT * FROM word WHERE id = :id")
    fun getById(id: Int): Word
}