package com.example.newsapp.repository

import androidx.lifecycle.LiveData
import com.example.newsapp.api.ApiServiceInterface
import com.example.newsapp.dataclass.Article
import com.example.newsapp.dataclass.NewsDataClass
import com.example.newsapp.db.NewsAppDao
import com.example.newsapp.utils.ApiCallErrorHandler
import javax.inject.Inject


//Note this implements test repository

//Also as against creating an instance of DB in the repository to access the Functions in DAO
//DI takes care of that for us by just injecting the news app DAO
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
            ApiCallErrorHandler.Error(e.message ?: " An Error Occurred fetching data from the API ")
        }
    }

    override suspend fun searchDataFromApiInTestRepository(
        searchQuery: String,
        pageNumber: Int
    ): ApiCallErrorHandler<NewsDataClass> {
        return try {
            val searchResponse = apiInterface.getAllNewsForSearchInInterface(searchQuery, pageNumber)
            val searchResponseBody = searchResponse.body()

            if (searchResponse.isSuccessful && searchResponseBody != null) {
                ApiCallErrorHandler.Success(searchResponseBody)
            } else {
                ApiCallErrorHandler.Error(searchResponse.message())
            }
        } catch (e: Exception){
            ApiCallErrorHandler.Error(e.message ?: " An Error Occurred fetching data from the API ")
        }
    }

    override suspend fun upsert(article: Article) {
        return newsAppDao.upsert(article)
    }

    override fun getNewsFromDBInMainRepository() : LiveData<List<Article>> {
        return newsAppDao.getAllDataInDB()
    }

    override suspend fun deleteNewsInDBFromRepo(article: Article) {
        return newsAppDao.deleteNewsFromDataBase(article)
    }
}