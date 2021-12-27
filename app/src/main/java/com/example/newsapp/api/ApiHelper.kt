package com.example.newsapp.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiHelper {

    private const val BASE_URL = "https://newsapi.org/"
    val gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()

    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
    val retrofit = builder.build()
    val apiClient: ApiClient = retrofit.create(ApiClient::class.java)
}