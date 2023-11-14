package com.example.myapplication.api

import com.example.myapplication.data.QuestionListResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface StackOverflowAPI {

    @GET("2.3/questions?order=desc&sort=creation&site=stackoverflow")
    fun getAllQuestions(): Call<QuestionListResponse>

    companion object {
        fun create() : StackOverflowAPI {
            val okhttp = OkHttpClient()
            val retrofit = Retrofit.Builder()
                .client(okhttp)
                .baseUrl("https://api.stackexchange.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(StackOverflowAPI::class.java)
        }
    }

}