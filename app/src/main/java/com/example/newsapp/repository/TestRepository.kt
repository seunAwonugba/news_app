package com.example.newsapp.repository

import com.example.newsapp.dataclass.NewsDataClass
import com.example.newsapp.utils.ApiCallErrorHandler

interface TestRepository {

    //Note i didn't pass the API key in the constructor, reason being that we have already initialised
    //it in the API Interface, just same we we initialised the country code and pageNumber
    //But these ones will be dynamic not constants
    suspend fun getDataFromApiInTestRepository(countryCode : String, pageNumber : Int) : ApiCallErrorHandler<NewsDataClass>
}