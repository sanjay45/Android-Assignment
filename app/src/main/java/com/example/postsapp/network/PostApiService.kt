package com.example.postsapp.network

import com.example.postsapp.model.PostApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface PostApiService {

    @GET("posts")
    suspend fun getPosts() : Response<PostApiResponse>
}