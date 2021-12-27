package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    lateinit var rvNews: RecyclerView

    lateinit var pbLoading: ProgressBar

    lateinit var searchView: SearchView
    var articleList: ArrayList<Article> = ArrayList()
    var isSearch = false

    private val newsViewModel: NewsViewModel by lazy {
        ViewModelProvider(this, NewsViewModel.NewsViewModelFactory(this.application)).get(
            NewsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pbLoading = findViewById(R.id.pb_loading)
        rvNews = findViewById(R.id.recyclerView)
        rvNews.setHasFixedSize(true)
        rvNews.layoutManager = LinearLayoutManager(this)
        rvNews.adapter = Adapter(articleList, this)
        loadDing()
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
            super.onBackPressed()
        }
    }
}