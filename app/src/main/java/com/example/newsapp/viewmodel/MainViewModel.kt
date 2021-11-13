package com.example.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.dataclass.Article
import com.example.newsapp.dataclass.NewsDataClass
import com.example.newsapp.repository.TestRepository
import com.example.newsapp.utils.ApiCallErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

//Please note i extend the test view model reason being that its an interface and i can easily
//implement its members

@HiltViewModel
class MainViewModel @Inject constructor(
    private var repository : TestRepository
) : ViewModel() {

    private val _breakingNews : MutableLiveData<ApiCallErrorHandler<NewsDataClass>> = MutableLiveData()
    var breakingNews : LiveData<ApiCallErrorHandler<NewsDataClass>> = _breakingNews

    private val _searchNews : MutableLiveData<ApiCallErrorHandler<NewsDataClass>> = MutableLiveData()
    var searchNews : LiveData<ApiCallErrorHandler<NewsDataClass>> = _searchNews

    //Implement Pagination here for the two end points
    var newsPagination = 1
    var searchNewsPagination = 1

    //create a variable that holds all responses received
    var receivedBreakingNewsResponse : NewsDataClass? = null
    var receivedSearchNewsResponse : NewsDataClass? = null

    init {
        getBreakingNewsInVM("us")
    }

    //Implement the function that now executes API call
    fun getBreakingNewsInVM(countryCode: String){
        viewModelScope.launch {
            //Before making the network call, lets emit the loading state to live data
            _breakingNews.postValue(ApiCallErrorHandler.Loading())
            val breakingNewsResponse = repository.getDataFromApiInTestRepository(countryCode, newsPagination)
            _breakingNews.postValue(breakingNewsApiResponseHandler(breakingNewsResponse))
        }
    }

    private fun breakingNewsApiResponseHandler(
        response: Response<NewsDataClass>
    ) : ApiCallErrorHandler<NewsDataClass>{
        val responseBody = response.body()
        if(response.isSuccessful && responseBody!=null){
            //when response is successful, increase current page number
                newsPagination++
            //originally receivedBreakingNewsResponse will be null at first load
            if (receivedBreakingNewsResponse == null){
                receivedBreakingNewsResponse = responseBody
            }
            else{
                val existingFetchedData = receivedBreakingNewsResponse?.articles
                val newFetchedData = responseBody.articles
                //At this point change from the generated data class property article List<Articles> to
                //MutableList<Article>
                //So the logic is make all ur checks, and finally add to existing list
                existingFetchedData?.addAll(newFetchedData)
            }
            return ApiCallErrorHandler.Success(receivedBreakingNewsResponse ?: responseBody)
        }
        return ApiCallErrorHandler.Error(response.message())
    }

    fun searchNewsInVM(searchQuery: String){
        viewModelScope.launch {
            //Before making the network call, lets emit the loading state to live data
            _searchNews.postValue(ApiCallErrorHandler.Loading())
            val searchNewsResponse = repository.searchDataFromApiInTestRepository(searchQuery,searchNewsPagination)
            _searchNews.postValue(searchNewsApiResponseHandler(searchNewsResponse))
        }
    }

    private fun searchNewsApiResponseHandler(
        response: Response<NewsDataClass>
    ) : ApiCallErrorHandler<NewsDataClass>{
        val responseBody = response.body()
        if(response.isSuccessful && responseBody!=null){
            //when response is successful, increase current page number
            searchNewsPagination++
            //originally receivedBreakingNewsResponse will be null at first load
            if (receivedSearchNewsResponse  == null){
                receivedSearchNewsResponse = responseBody
            }
            else{
                val existingFetchedData = receivedSearchNewsResponse?.articles
                val newFetchedData = responseBody.articles
                //At this point change from the generated data class property article List<Articles> to
                //MutableList<Article>
                //So the logic is make all ur checks, and finally add to existing list
                existingFetchedData?.addAll(newFetchedData)
            }
            return ApiCallErrorHandler.Success(receivedSearchNewsResponse ?: responseBody)
        }
        return ApiCallErrorHandler.Error(response.message())
    }

    //upsert function
    fun upsert(article: Article) = viewModelScope.launch {
        repository.upsert(article)
    }

    fun getDataFromDB() = repository.getNewsFromDBInMainRepository()

    fun deleteDataFromDB(article: Article) = viewModelScope.launch {
        repository.deleteNewsInDBFromRepo(article)
    }
}