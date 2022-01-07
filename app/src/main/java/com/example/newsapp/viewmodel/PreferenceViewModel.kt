package com.example.newsapp.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.respository.PreferenceRespository
import com.example.newsapp.respository.QuoteRespository
import java.lang.IllegalArgumentException

class PreferenceViewModel(val application: Application) : ViewModel() {

    private val preferenceRespository: PreferenceRespository = PreferenceRespository(application)

    fun checkPassword(password: String): Boolean = preferenceRespository.checkPassword(password)

    fun getPassCodeFromSharedPreferences(): String =
        preferenceRespository.getPassCodeFromSharedPreferences()

    fun savePasscode(password: String) = preferenceRespository.savePasscode(password)

    class PreferenceViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PreferenceViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PreferenceViewModel(application) as T
            }
            throw IllegalArgumentException("Unable construce viewmodel")
        }
    }
}