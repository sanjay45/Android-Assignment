package com.example.postsapp.repository

import com.example.postsapp.model.Post
import com.example.postsapp.network.PostApi
import com.example.postsapp.network.PostApiService

object PostRepository {
    suspend fun getPosts() = PostApi.api.getPosts()
}