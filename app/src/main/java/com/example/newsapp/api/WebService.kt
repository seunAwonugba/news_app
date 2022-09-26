package com.example.newsapp.api

import com.example.newsapp.constants.Constants.API_KEY
import com.example.newsapp.constants.Constants.EVERY_NEWS_END_POINT
import com.example.newsapp.constants.Constants.HEADLINE_END_POINT
import com.example.newsapp.constants.Constants.PAGE_SIZE
import com.example.newsapp.data.remote.NewsDataClass
import com.example.newsapp.data.ui.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET(HEADLINE_END_POINT)
    suspend fun getHeadlineNews(
        @Query("country") countryCode : String,
        @Query("page") page : Int,
        @Query("pageSize") pageSize : Int,
        @Query("apiKey") apiKey : String = API_KEY
    ): NewsDataClass

    @GET(EVERY_NEWS_END_POINT)
    suspend fun getAllNewsForSearchInInterface(
        @Query("q") searchQuery : String,
        @Query("page") pageNumber: Int = 1,
        @Query("apiKey") apiKey: String = API_KEY
    ) : Response<NewsDataClass>
}

fun NewsDataClass.Article.toNewsResponse() = NewsResponse(
    articleId = this.newsArticleId,
    author = this.author,
    content = this.content,
    description = this.description,
    publishedAt = this.publishedAt,
    sourceName = this.source?.name,
    sourceId = this.source?.id,
    title = this.title,
    url = this.url,
    image = this.urlToImage
)