package com.example.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.news.databinding.ActivityMainBinding
import com.example.news.network.Article
import com.example.news.network.RetrofitInstance
import com.example.news.utils.NewsAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: NewsAdapter
    val list = ArrayList<Article>()
    val mainScope = MainScope()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = NewsAdapter()
        binding.newsRv.layoutManager = LinearLayoutManager(this)
        binding.newsRv.adapter = adapter

        fetchData()
    }

    private fun fetchData() {
        mainScope.launch(Dispatchers.Main) {
            // Coroutine code here
            getData()
            adapter.submitList(list)
        }
    }

    private suspend fun getData() {
        withContext(Dispatchers.IO) {
            val response = RetrofitInstance.api.getTopNews()
            Log.d("Network Call TAG", "getData: ${response.isSuccessful}")

            if (response.isSuccessful) {
                response.body()?.let { list.addAll(it.articles) }
            } else {
                Toast.makeText(
                    this@MainActivity,
                    response.errorBody().toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel() // Cancel the coroutine scope to avoid leaks
    }
}