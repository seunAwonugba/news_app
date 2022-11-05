package com.example.newsapp.data.ui

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "news_article_table"
)
data class NewsResponse(
    @PrimaryKey(autoGenerate = true)
    val articleId : Int,
    val author : String,
    val content : String,
    val description : String,
    val publishedAt : String,
    val sourceName : String,
    val sourceId : String,
    val title : String,
    val url : String,
    val image : String
)
