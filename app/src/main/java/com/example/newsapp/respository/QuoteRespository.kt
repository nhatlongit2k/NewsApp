package com.example.newsapp.respository

import android.app.Application
import com.example.newsapp.api.ApiHelper

class QuoteRespository(app: Application) {

    suspend fun getTopHeadLine(country: String, ApiKey: String) = ApiHelper.apiClient.getTopHeadLine(country, ApiKey)

    suspend fun getSearch(query: String, apiKey: String) = ApiHelper.apiClient.getSearch(query, apiKey)
}