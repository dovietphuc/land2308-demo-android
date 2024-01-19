package com.example.land2308githubuser

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GithubApiProvider {
    companion object {
        var githubApi: GithubUserApi? = null
        var myRetrofit: Retrofit? = null

        fun getRetrofit() : Retrofit {
            if(myRetrofit == null) {
                val okHttpClient = OkHttpClient.Builder().build()
                myRetrofit = Retrofit.Builder()
                    .baseUrl("https://api.github.com")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return myRetrofit!!
        }

        fun getGithubUserApi() : GithubUserApi {
            if(githubApi == null) {
                githubApi = getRetrofit().create(GithubUserApi::class.java)
            }
            return githubApi!!
        }
    }
}