package com.example.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    val breakingNews : LiveData<ApiCallErrorHandler<NewsDataClass>> = _breakingNews

    //Implement Pagination here
    var newsPagination = 1

    //Implement the function that now executes API call
    fun getBreakingNewsInVM(countryCode: String){
        viewModelScope.launch {
            //Before making the network call, lets emit the loading state to live data
            _breakingNews.postValue(ApiCallErrorHandler.Loading())
            val breakingNewsResponse = repository.getDataFromApiInTestRepository(countryCode, newsPagination)
            _breakingNews.postValue(breakingNewsResponse)
        }
    }
}