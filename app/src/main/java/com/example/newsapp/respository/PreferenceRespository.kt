package com.example.newsapp.respository

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class PreferenceRespository(val app: Application) {

    val passCodePref = "pass_code_pref"
    val passCode = "pass_code"

    fun checkPassword(password: String): Boolean {
        if (getPassCodeFromSharedPreferences().equals(password))
            return true
        return false
    }

    fun getPassCodeFromSharedPreferences(): String {
        val sharedPreferences: SharedPreferences =
            app.getSharedPreferences(passCodePref, Context.MODE_PRIVATE)
        val passCode: String = sharedPreferences.getString(passCode, "").toString()
        return passCode
    }

    fun savePasscode(password: String) {
        val sharedPreferences: SharedPreferences =
            app.getSharedPreferences(passCodePref, Context.MODE_PRIVATE)
        val edit: SharedPreferences.Editor = sharedPreferences.edit()
        edit.putString(passCode, password)
        edit.apply()
    }
}