package com.example.land2308githubuser

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow

class MainActivityViewModel : ViewModel() {
    val githubApi = GithubApiProvider.getGithubUserApi()

    fun allUsers() : Flow<List<GithubUser>?> = flow {
        emit(githubApi.getAllUsers().execute().body())
    }

}