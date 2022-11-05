package com.example.newsapp.data.remote

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@kotlinx.serialization.Serializable
data class NewsDataClass(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
){
    @Parcelize
    @Entity(
        tableName = "news_article_table"
    )
    @kotlinx.serialization.Serializable
    data class Article(
        @PrimaryKey(autoGenerate = true)
        val newsArticleId: Int? = null,
        val author: String?,
        val content: String?,
        val description: String?,
        val publishedAt: String?,
        val source: Source?,
        val title: String?,
        val url: String?,
        val urlToImage: String?
    ) : Parcelable{

        @kotlinx.parcelize.Parcelize
        @kotlinx.serialization.Serializable
        data class Source(
            val id: String?,
            val name: String?
        ) : Parcelable
    }

}