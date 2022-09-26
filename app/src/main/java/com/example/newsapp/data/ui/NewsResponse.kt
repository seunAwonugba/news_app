package com.example.newsapp.data.ui

data class NewsResponse(
    val articleId : Int? = null,
    val author : String? = null,
    val content : String? = null,
    val description : String? = null,
    val publishedAt : String? = null,
    val sourceName : String? = null,
    val sourceId : String? = null,
    val title : String? = null,
    val url : String? = null,
    val image : String? = null
)
