package com.example.newsapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapp.api.WebService
import com.example.newsapp.constants.Constants.PAGE_SIZE
import com.example.newsapp.data.ui.NewsResponse
import com.example.newsapp.db.NewsAppDao
import com.example.newsapp.paging.HeadlineNewsPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


//Note this implements test repository

//Also as against creating an instance of DB in the repository to access the Functions in DAO
//DI takes care of that for us by just injecting the news app DAO
class MainRepositoryImpl @Inject constructor(
    private val webService : WebService,
    private val newsAppDao: NewsAppDao
    ) : MainRepository {

    override suspend fun getHeadlineNews(countryCode: String): Flow<PagingData<NewsResponse>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = true),
            pagingSourceFactory = {
                HeadlineNewsPagingSource(webService = webService, countryCode = countryCode)
            }
        ).flow
    }


//    override fun getFeatures(): Flow<PagingData<Features>> {
//        return Pager(
//            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = true),
//            pagingSourceFactory = { FeaturesPagingSource(webService = webService) })
//            .flow
//    }


    //    override suspend fun searchDataFromApiInTestRepository(
//        searchQuery: String,
//        pageNumber: Int
//    ) = web.getAllNewsForSearchInInterface(searchQuery, pageNumber)
//
//    override suspend fun upsert(article: Article) {
//        return newsAppDao.upsert(article)
//    }
//
//    override fun getNewsFromDBInMainRepository() : LiveData<List<Article>> {
//        return newsAppDao.getAllDataInDB()
//    }
//
//    override suspend fun deleteNewsInDBFromRepo(article: Article) {
//        return newsAppDao.deleteNewsFromDataBase(article)
//    }

}