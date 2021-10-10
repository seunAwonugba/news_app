package com.example.newsapp.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "news_article_table"
)
data class Article(
    @PrimaryKey(autoGenerate = true)
    val newsArticleId: Int? = null,
    val author: Any,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
)