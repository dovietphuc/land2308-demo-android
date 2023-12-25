package com.example.demoroomdatabase.model.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word")
data class Word(
    @ColumnInfo(name = "name")
    val name: String,
//    @ColumnInfo()
    val description: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
