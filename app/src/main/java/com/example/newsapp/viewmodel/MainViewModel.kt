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

    //Implement Pagination here
    var newsPagination = 1

    var searchNewsPagination = 1

    init {
        getBreakingNewsInVM("us")
    }

    //Implement the function that now executes API call
    private fun getBreakingNewsInVM(countryCode: String){
        viewModelScope.launch {
            //Before making the network call, lets emit the loading state to live data
            _breakingNews.postValue(ApiCallErrorHandler.Loading())
            val breakingNewsResponse = repository.getDataFromApiInTestRepository(countryCode, newsPagination)
            _breakingNews.postValue(breakingNewsResponse)
        }
    }

    fun searchNewsInVM(searchQuery: String){
        viewModelScope.launch {
            //Before making the network call, lets emit the loading state to live data
            _searchNews.postValue(ApiCallErrorHandler.Loading())
            val searchNewsResponse = repository.searchDataFromApiInTestRepository(searchQuery,searchNewsPagination)
            _searchNews.postValue(searchNewsResponse)
        }
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