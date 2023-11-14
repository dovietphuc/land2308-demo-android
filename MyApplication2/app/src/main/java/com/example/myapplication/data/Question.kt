package com.example.myapplication.data

data class Question (
    val id: Int,
    val title: String,
    val viewCount: Int,
    val createdTime: Long,
    val link: String
)