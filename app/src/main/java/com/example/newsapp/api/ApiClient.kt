package com.example.newsapp.api

import com.example.newsapp.models.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {

    //https://newsapi.org/v2/top-headlines?country=us&apiKey=c12e2e2267ab40a883a9f151b7501b0f

    @GET("v2/top-headlines")
    suspend fun getTopHeadLine(@Query("country") country: String, @Query("apiKey") apiKey: String): Response<News>

    @GET("v2/everything")
    suspend fun getSearch(@Query("q") query: String, @Query("apiKey") apiKey: String): Response<News>
}