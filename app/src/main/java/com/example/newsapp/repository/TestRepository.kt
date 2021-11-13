package com.example.newsapp.repository

import androidx.lifecycle.LiveData
import com.example.newsapp.dataclass.Article
import com.example.newsapp.dataclass.NewsDataClass
import com.example.newsapp.utils.ApiCallErrorHandler
import retrofit2.Response

interface TestRepository {

    //Note i didn't pass the API key in the constructor, reason being that we have already initialised
    //it in the API Interface, just same we we initialised the country code and pageNumber
    //But these ones will be dynamic not constants
    suspend fun getDataFromApiInTestRepository(countryCode : String, pageNumber : Int) : Response<NewsDataClass>

    suspend fun searchDataFromApiInTestRepository(searchQuery : String, pageNumber: Int) : Response<NewsDataClass>

    //just pass only data you want to add to DB

    suspend fun upsert(article: Article)

    //Getting data from DB runs on Livedata normally
    fun getNewsFromDBInMainRepository() : LiveData<List<Article>>

    suspend fun deleteNewsInDBFromRepo(article: Article)
}