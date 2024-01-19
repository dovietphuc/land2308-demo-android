package com.example.land2308githubuser

import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubUserApi {
    @GET("/users")
    fun getAllUsers() : Call<List<GithubUser>>
}