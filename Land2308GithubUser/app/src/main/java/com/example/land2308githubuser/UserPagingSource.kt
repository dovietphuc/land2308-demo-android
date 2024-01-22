package com.example.land2308githubuser

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState

class UserPagingSource : PagingSource<Int, GithubUser>() {

    val githubApi = GithubApiProvider.getGithubUserApi()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubUser> {
        var key = params.key
        if(key == null) {
            key = 0
        }
        val data = githubApi.getAllUsers(key, params.loadSize)
        return LoadResult.Page(
            data,
            null,
            data.lastOrNull()!!.id
        )
    }

    override fun getRefreshKey(state: PagingState<Int, GithubUser>): Int? {
        state.lastItemOrNull()?.let {
            return it.id
        }
        return null
    }
}