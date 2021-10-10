package com.example.newsapp.repository

import com.example.newsapp.api.ApiServiceInterface
import com.example.newsapp.dataclass.Article
import com.example.newsapp.dataclass.NewsDataClass
import com.example.newsapp.db.NewsAppDao
import com.example.newsapp.utils.ApiCallErrorHandler
import javax.inject.Inject


//Note this implements test repository
class MainRepository @Inject constructor(
    private val apiInterface : ApiServiceInterface,
    private val newsAppDao: NewsAppDao
    ) : TestRepository {
    override suspend fun getDataFromApiInTestRepository(
        countryCode: String,
        pageNumber: Int
    ): ApiCallErrorHandler<NewsDataClass> {
        return try {
            val receivedApiResponse = apiInterface.getHeadlineNewsInInterface(countryCode, pageNumber)
            val receivedApiResult = receivedApiResponse.body()

            if (receivedApiResponse.isSuccessful && receivedApiResult != null) {
                ApiCallErrorHandler.Success(receivedApiResult)
            } else {
                ApiCallErrorHandler.Error(receivedApiResponse.message())
            }
        } catch (e: Exception){
            ApiCallErrorHandler.Error(e.message ?: "An Error Occurred fetching data from the API ")
        }
    }

    suspend fun upsertInMainRepository(article: Article) = newsAppDao.upsert(article)

    fun getNewsFromDBInMainRepository() = newsAppDao.getAllDataInDB()

    suspend fun deleteNewsInDBFromRepo(article: Article) = newsAppDao.deleteNewsFromDataBase(article)




}