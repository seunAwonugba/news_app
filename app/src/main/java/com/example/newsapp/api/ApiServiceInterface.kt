package com.example.newsapp.api

import com.example.newsapp.constants.Constants.API_KEY
import com.example.newsapp.constants.Constants.EVERY_NEWS_END_POINT
import com.example.newsapp.constants.Constants.HEADLINE_END_POINT
import com.example.newsapp.dataclass.NewsDataClass
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceInterface {

    @GET(HEADLINE_END_POINT)
    suspend fun getHeadlineNewsInInterface(
        @Query("country") countryCode : String = "us",
        @Query("page") pageNumber : Int = 1,
        @Query("apiKey") apiKey : String = API_KEY
    ): Response<NewsDataClass>

    @GET(EVERY_NEWS_END_POINT)
    suspend fun getAllNewsForSearchInInterface(
        @Query("q") searchQuery : String,
        @Query("page") pageNumber: Int = 1,
        @Query("apiKey") apiKey: String = API_KEY
    ) : Response<NewsDataClass>
}