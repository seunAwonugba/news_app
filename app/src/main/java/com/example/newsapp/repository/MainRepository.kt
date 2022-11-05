package com.example.newsapp.repository

import androidx.paging.PagingData
import com.example.newsapp.data.ui.NewsResponse
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    suspend fun getHeadlineNews(countryCode : String) : Flow<PagingData<NewsResponse>>
//
    fun searchNews(query : String) : Flow<List<NewsResponse>>
//
//    //just pass only data you want to add to DB
//
//    suspend fun upsert(article: NewsDataClass.Article)
//
//    //Getting data from DB runs on Livedata normally
//    fun getNewsFromDBInMainRepository() : LiveData<List<NewsDataClass.Article>>
//
//    suspend fun deleteNewsInDBFromRepo(article: NewsDataClass.Article)
}