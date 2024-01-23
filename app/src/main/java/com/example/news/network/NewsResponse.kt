package com.example.news.network

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)