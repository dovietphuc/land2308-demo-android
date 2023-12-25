package com.example.demoroomdatabase.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.demoroomdatabase.model.dao.WordDAO
import com.example.demoroomdatabase.model.entity.Word

@Database(entities = [Word::class], version = 1)
abstract class WordDatabase : RoomDatabase() {
    abstract fun wordDAO(): WordDAO

    companion object {
        private var INSTANCE: WordDatabase? = null

        public fun getInstance(context: Context) : WordDatabase {
            if(INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            WordDatabase::class.java, "word.db")
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}