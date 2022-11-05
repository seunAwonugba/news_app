package com.example.newsapp.di

import com.example.newsapp.BuildConfig
import com.example.newsapp.constants.Constants.BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(makeLoggingInterceptor())
            .addInterceptor(ErrorInterceptor()) // always add this last, so okhttp executes it last
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    private fun makeLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }

    class ErrorInterceptor : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val response = chain.proceed(chain.request())
            if (response.isSuccessful.not()) {
                val errorMsg = getErrorMessage(response.body)
                throw ServerErrorException(errorMsg)
            }
            return response
        }

        private fun getErrorMessage(responseBody: ResponseBody?): String {
            return try {
                val body = JSONObject(responseBody?.string() ?: "")
                return if (body.has("error") && body.has("message")) {
                    "${body.getString("error")} - ${body.getString("message")}"
                } else {
                    body.toString()
                }
            } catch (e: JSONException) {
                // could not parse response body, returning a generic error
                "An error occurred, please try again later"
            }
        }

        class ServerErrorException(message: String) : IOException(message)
    }

    private val json = Json { ignoreUnknownKeys = true }

    @ExperimentalSerializationApi
    @Provides
    @Singleton
    fun providerConverterFactory(): Converter.Factory {
        return json.asConverterFactory("application/json".toMediaType())
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }
}