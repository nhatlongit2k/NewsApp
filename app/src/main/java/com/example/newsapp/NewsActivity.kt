package com.example.newsapp

import android.app.ActivityManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.models.Article
import com.example.newsapp.models.News
import com.example.newsapp.viewmodel.NewsViewModel

class NewsActivity : AppCompatActivity() {

    lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        webView = findViewById(R.id.web_view)
        val url: String? = intent.getStringExtra("linkUrl")
        Log.d("TAG", "url: $url ")
        if (url != null) {
            webView.loadUrl(url)
        }

        webView.webViewClient = WebViewClient()
    }

    override fun onResume() {
        if (MainActivity.locking == true){
            val intent: Intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            MainActivity.locking = false
        }
        super.onResume()
    }

    override fun onUserLeaveHint() {

        val mngr = getSystemService(ACTIVITY_SERVICE) as ActivityManager


        val taskList: List<ActivityManager.RunningTaskInfo> = mngr.getRunningTasks(10)
//        Log.d("TAG", "${taskList[0].numActivities}: ")
        Log.d("TAG", "${taskList[0].topActivity!!.className}: == ${this.javaClass.name} ")
        if (taskList[0].numActivities == 1) {
//            Log.d("TAG", "onUserLeaveHint: ")
//            val intent: Intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
            MainActivity.locking = true
        }
//        val mngr = getSystemService(ACTIVITY_SERVICE) as ActivityManager
//
//        val taskList = mngr.getRunningTasks(10)
//
//        if (taskList[0].numActivities == 1 && taskList[0].topActivity!!.className == this.javaClass.name) {
//            val intent: Intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//        }
        super.onUserLeaveHint()
    }
}