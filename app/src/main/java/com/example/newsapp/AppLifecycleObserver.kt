package com.example.newsapp

import android.content.Context
import android.content.Intent
import androidx.lifecycle.*

class AppLifecycleObserver(val context: Context) : DefaultLifecycleObserver {

    override fun onStart(owner: LifecycleOwner) {
        val intent = Intent(context, LoginActivity::class.java)
        context.startActivity(intent)
        super.onStart(owner)
    }
}