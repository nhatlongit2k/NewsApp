package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.models.Article
import com.example.newsapp.viewmodel.NewsViewModel
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.newsapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    lateinit var searchView: SearchView
    lateinit var binding: ActivityMainBinding
    lateinit var appLifecycleObserver: AppLifecycleObserver
    var articleList: ArrayList<Article> = ArrayList()
    var isSearch = false

    private val newsViewModel: NewsViewModel by lazy {
        ViewModelProvider(this, NewsViewModel.NewsViewModelFactory(this.application)).get(
            NewsViewModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        appLifecycleObserver = AppLifecycleObserver(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(appLifecycleObserver)
        binding.rvNew.setHasFixedSize(true)
        binding.rvNew.layoutManager = LinearLayoutManager(this)
        binding.rvNew.adapter = Adapter(articleList, this)
        loading()
        binding.btTryAgain.setOnClickListener {
            loading()
            binding.btTryAgain.visibility = View.GONE
        }
    }

    private fun loading() {
        binding.pbLoading.visibility = View.VISIBLE
        newsViewModel.getTopHeadLine().observe(this, { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    binding.pbLoading.visibility = View.GONE
                    resource.data?.let { topHeadLineData ->
                        val result = topHeadLineData.body()
                        if (result != null) {
                            articleList = result.articles
                        }
                        binding.rvNew.adapter = Adapter(articleList, this)
                    }
                }
                Status.ERROR -> {
                    binding.btTryAgain.visibility = View.VISIBLE
                    binding.pbLoading.visibility = View.GONE
                    Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (menu != null) {
            menuInflater.inflate(R.menu.menu, menu)
            val search = menu.findItem(R.id.menu_search)
            searchView = (search?.actionView as SearchView)
            searchView.isSubmitButtonEnabled = true
            searchView.setOnQueryTextListener(this)
        }
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null && query.isNotEmpty()) {
            binding.pbLoading.visibility = View.VISIBLE
            newsViewModel.getSearch(query).observe(this, { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { searchData ->
                            var searchList: ArrayList<Article> = ArrayList()
                            val result = searchData.body()
                            if (result != null) {
                                searchList = result.articles
                            }
                            binding.rvNew.adapter = Adapter(searchList, this)
                            isSearch = true
                            binding.pbLoading.visibility = View.GONE
                        }
                    }
                    Status.ERROR -> {
                        binding.pbLoading.visibility = View.GONE
                        Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
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
        if (isSearch) {
            loading()
            searchView.setQuery("", false)
            isSearch = false
        } else {
            ProcessLifecycleOwner.get().lifecycle.removeObserver(appLifecycleObserver)
            finishAffinity()
            super.onBackPressed()
        }
    }
}