package com.example.newsapp.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.respository.PreferenceRespository
import com.example.newsapp.respository.QuoteRespository
import java.lang.IllegalArgumentException

class PreferenceViewModel(val application: Application) {

    private val preferenceRespository: PreferenceRespository = PreferenceRespository(application)

    fun checkPassword(password: String): Boolean = preferenceRespository.checkPassword(password)

    fun getPassCodeFromSharedPreferences(): String =
        preferenceRespository.getPassCodeFromSharedPreferences()

    fun savePasscode(password: String) = preferenceRespository.savePasscode(password)
}