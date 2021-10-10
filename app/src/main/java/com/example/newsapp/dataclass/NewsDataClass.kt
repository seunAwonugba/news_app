package com.example.newsapp.dataclass

data class NewsDataClass(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)