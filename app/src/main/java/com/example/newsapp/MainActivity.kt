package com.example.newsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.View
import android.widget.ProgressBar

import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.models.Article
import com.example.newsapp.viewmodel.NewsViewModel
import android.view.WindowManager
import android.app.ActivityManager
import android.app.ActivityManager.RunningTaskInfo
import android.widget.Button


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    lateinit var rvNews: RecyclerView

    lateinit var pbLoading: ProgressBar

    lateinit var searchView: SearchView

    lateinit var btRetry: Button
    var articleList: ArrayList<Article> = ArrayList()
    var isSearch = false

    companion object{
        var locking = true
    }

    private val newsViewModel: NewsViewModel by lazy {
        ViewModelProvider(this, NewsViewModel.NewsViewModelFactory(this.application)).get(
            NewsViewModel::class.java)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val intent: Intent = Intent(this, LoginActivity::class.java)
////        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        startActivity(intent)

        btRetry = findViewById(R.id.bt_try_again)
        pbLoading = findViewById(R.id.pb_loading)
        rvNews = findViewById(R.id.recyclerView)
        rvNews.setHasFixedSize(true)
        rvNews.layoutManager = LinearLayoutManager(this)
        rvNews.adapter = Adapter(articleList, this)
        loadDing()

        btRetry.setOnClickListener {
            loadDing()
            btRetry.visibility = View.GONE
        }
    }

    private fun loadDing() {
        pbLoading.visibility = View.VISIBLE
        newsViewModel.getTopHeadLine().observe(this, {
            it.let { resource ->
                when(resource.status){
                    Status.SUCCESS ->{
                        pbLoading.visibility = View.GONE
                        resource.data?.let { topHeadLineData->
                            articleList = topHeadLineData.body()?.articles!!
                            rvNews.adapter = Adapter(articleList, this)
                        }
                    }
                    Status.ERROR->{
                        btRetry.visibility = View.VISIBLE
                        pbLoading.visibility = View.GONE
                        Log.d("TAG", "onCreate: ${it.message}")
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val search = menu?.findItem(R.id.menu_search)
        searchView = (search?.actionView as? SearchView)!!
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query!=null && query.length > 0){
            pbLoading.visibility = View.VISIBLE
            newsViewModel.getSearch(query).observe(this,{
                it.let { resource ->
                    when(resource.status){
                        Status.SUCCESS->{
                            resource.data?.let { searchData->
                                var searchList: ArrayList<Article> = ArrayList()
                                searchList = searchData.body()?.articles!!
                                rvNews.adapter = Adapter(searchList, this)
                                isSearch = true
                                pbLoading.visibility = View.GONE
                            }
                        }
                        Status.ERROR->{
                            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    override fun onBackPressed() {
        if(isSearch == true){
            loadDing()
            searchView.setQuery("", false)
            isSearch = false
        }else{
            locking=true
            finishAffinity()
            super.onBackPressed()
        }
    }

    override fun onResume() {
        if (locking == true){
            val intent: Intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            locking = false
        }
        super.onResume()
    }

    override fun onUserLeaveHint() {
//        if(isTaskRoot){
//            Log.d("TAG", "onUserLeaveHint: ")
//            val intent: Intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//        }

        val mngr = getSystemService(ACTIVITY_SERVICE) as ActivityManager


        val taskList: List<ActivityManager.RunningTaskInfo> = mngr.getRunningTasks(10)
//        Log.d("TAG", "${taskList[0].numActivities}: ")
//        Log.d("TAG", "${taskList[0].topActivity!!.className}: == ${this.javaClass.name} ")
        if (taskList[0].numActivities == 1) {
            Log.d("TAG", "onUserLeaveHint: ")
//            val intent: Intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
            locking = true
        }
        super.onUserLeaveHint()
    }
}