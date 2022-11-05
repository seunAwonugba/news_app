package com.example.newsapp.data.remote

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@kotlinx.serialization.Serializable
data class NewsResponseDto(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
){
//    @Entity(
//        tableName = "news_article_table"
//    )
    @kotlinx.serialization.Serializable
    data class Article(
//        @PrimaryKey(autoGenerate = true)
        val newsArticleId: Int = 0,
        val author: String = "",
        val content: String = "",
        val description: String = "",
        val publishedAt: String = "",
        val source: Source = Source(),
        val title: String = "",
        val url: String = "",
        val urlToImage: String = ""
    ){

        @kotlinx.serialization.Serializable
        data class Source(
            val id: String = "",
            val name: String = ""
        )
    }

}