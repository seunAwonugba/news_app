package com.example.newsapp.di

import android.content.Context
import androidx.room.Room
import com.example.newsapp.api.WebService
import com.example.newsapp.constants.Constants
import com.example.newsapp.db.NewsAppDao
import com.example.newsapp.db.NewsAppDataBase
import com.example.newsapp.repository.MainRepository
import com.example.newsapp.repository.MainRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideWebService(retrofit: Retrofit) : WebService = retrofit.create()


    @Singleton
    @Provides
    fun provideMainRepository(
        webService: WebService,
        newsAppDao: NewsAppDao
    ) : MainRepository = MainRepositoryImpl(webService, newsAppDao)

    @Singleton
    @Provides
    fun provideMovieAppDataBaseInModule(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        NewsAppDataBase::class.java,
        Constants.DATABASE_NAME
    ).build()

    /**
     * Note what i really need is the NewsAppDao, because it contains
     * functions i can use to access the DataBase itself
     *
     * so now DI knows how to create the DB i need to access the DAO to Access its functions
     */

    @Singleton
    @Provides
    fun provideNewsAppDaoInModule(dataBase: NewsAppDataBase) = dataBase.instanceOfNewsAppDaoInDB()

}