package com.example.newsapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.newsapp.Resource
import com.example.newsapp.respository.QuoteRespository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.IllegalArgumentException

class NewsViewModel(application: Application): ViewModel() {

    private val quoteRespository: QuoteRespository = QuoteRespository(application)

    fun getTopHeadLine() = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        try {
            emit(Resource.success(quoteRespository.getTopHeadLine("us", "c12e2e2267ab40a883a9f151b7501b0f")))
        }catch (e: Exception){
            Log.d("TAG", "getTopHeadLine: ")
            emit(Resource.error(null, e.message ?: "Error"))
        }
    }

    fun getSearch(query: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(quoteRespository.getSearch(query, "c12e2e2267ab40a883a9f151b7501b0f")))
        }catch (e: Exception){
            emit(Resource.error(null, e.message ?: "Error"))
        }
    }

    class NewsViewModelFactory(private val application: Application): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(NewsViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return NewsViewModel(application) as T
            }
            throw IllegalArgumentException("Unable construce viewmodel")
        }

    }
}