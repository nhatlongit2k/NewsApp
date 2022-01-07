package com.example.newsapp.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.newsapp.Resource
import com.example.newsapp.respository.QuoteRespository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.IllegalArgumentException

class NewsViewModel(val application: Application) : ViewModel() {

    val APIKey = "c12e2e2267ab40a883a9f151b7501b0f"
    val topHeadLines = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    quoteRespository.getTopHeadLine(
                        "us",
                        APIKey
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(null, e.message ?: "Error"))
        }
    }

    private val quoteRespository: QuoteRespository = QuoteRespository(application)

    fun getTopHeadLine() = topHeadLines

    fun getSearch(query: String) = liveData(Dispatchers.IO) {
        try {
            emit(
                Resource.success(
                    quoteRespository.getSearch(
                        query,
                        APIKey
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(null, e.message ?: "Error"))
        }
    }

    class NewsViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NewsViewModel(application) as T
            }
            throw IllegalArgumentException("Unable construce viewmodel")
        }
    }
}