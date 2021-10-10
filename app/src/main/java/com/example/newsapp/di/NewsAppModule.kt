package com.example.newsapp.di

import android.content.Context
import androidx.room.Room
import com.example.newsapp.api.ApiServiceInterface
import com.example.newsapp.constants.Constants.BASE_URL
import com.example.newsapp.constants.Constants.DATABASE_NAME
import com.example.newsapp.db.NewsAppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NewsAppModule {

    //Retrofit dependencies

    @Singleton
    @Provides
    fun provideNewsApiInAppModule(): ApiServiceInterface {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level= HttpLoggingInterceptor.Level.BODY
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiServiceInterface::class.java)

    }

    //Room dependencies

    @Singleton
    @Provides
    fun provideMovieAppDataBaseInModule(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        NewsAppDataBase::class.java,
        DATABASE_NAME
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