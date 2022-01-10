package com.example.newsapp

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.*

class AppLifecycleObserver() : Application(), DefaultLifecycleObserver {
    override fun onStart(owner: LifecycleOwner) {
        val intent = Intent(applicationContext, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        super.onStart(owner)
    }

    override fun onCreate() {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        super<Application>.onCreate()
    }
}