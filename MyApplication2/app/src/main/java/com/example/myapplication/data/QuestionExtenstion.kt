package com.example.myapplication.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.api.StackOverflowAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun getAllQuestions() : LiveData<List<Question>> {
    val liveData = MutableLiveData<List<Question>>(emptyList())

    StackOverflowAPI.create().getAllQuestions().enqueue(object :
        Callback<QuestionListResponse> {
        override fun onResponse(
            call: Call<QuestionListResponse>,
            response: Response<QuestionListResponse>
        ) {
            liveData.postValue(response.body()?.list)
        }

        override fun onFailure(call: Call<QuestionListResponse>, t: Throwable) {
        }

    })

    return liveData
}