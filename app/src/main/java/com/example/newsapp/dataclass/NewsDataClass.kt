package com.example.newsapp.dataclass

data class NewsDataClass(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)