package com.example.newsapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsapp.data.ui.NewsResponse

@Dao
interface NewsAppDao {

    //Function to add data to database and also update database by its ID, hence the LONG return

    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun upsert(article: NewsResponseDto.Article)
    suspend fun upsert(newsResponse: NewsResponse)

    //Function to retrieve data from database, its used to update views

//    @Query("SELECT * FROM news_article_table ORDER BY newsArticleId DESC")
    @Query("SELECT * FROM news_article_table ORDER BY articleId DESC")
    fun getAllDataInDB() : LiveData<List<NewsResponse>>

    //Function to delete data from database

    @Delete
//    suspend fun deleteNewsFromDataBase(article: NewsResponseDto.Article)
    suspend fun deleteNewsFromDataBase(newsResponse: NewsResponse)
}