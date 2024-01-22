package com.example.land2308githubuser

import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubUserApi {
    @GET("/users")
    suspend fun getAllUsers(@Query("since") since: Int,
                    @Query("per_page") pageSize: Int) : List<GithubUser>
}