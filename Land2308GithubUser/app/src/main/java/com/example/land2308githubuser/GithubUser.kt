package com.example.land2308githubuser

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GithubUser(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("login")
    @Expose
    val login: String,
    @SerializedName("avatar_url")
    @Expose
    val avatarUrl: String
)
