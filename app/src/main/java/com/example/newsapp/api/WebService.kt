package com.example.newsapp.api

import com.example.newsapp.BuildConfig
import com.example.newsapp.constants.Constants.API_KEY
import com.example.newsapp.constants.Constants.EVERY_NEWS_END_POINT
import com.example.newsapp.constants.Constants.HEADLINE_END_POINT
import com.example.newsapp.data.remote.NewsResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WebService {

    @Headers("Authorization: ${BuildConfig.API_KEY} ")
    @GET(HEADLINE_END_POINT)
    suspend fun getHeadlineNews(
        @Query("country") countryCode : String,
        @Query("page") page : Int,
        @Query("pageSize") pageSize : Int,
    ): NewsResponseDto

    @GET(EVERY_NEWS_END_POINT)
    suspend fun getAllNewsForSearchInInterface(
        @Query("q") searchQuery : String,
        @Query("page") pageNumber: Int = 1,
        @Query("apiKey") apiKey: String = API_KEY
    ) : Response<NewsResponseDto>
}