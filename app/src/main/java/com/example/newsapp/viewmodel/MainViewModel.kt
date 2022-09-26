package com.example.newsapp.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsapp.data.remote.NewsDataClass
import com.example.newsapp.data.ui.NewsResponse
import com.example.newsapp.repository.MainRepository
import com.example.newsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private var mainRepository : MainRepository
) : ViewModel() {


    private val _headlineNews = MutableStateFlow<PagingData<NewsResponse>>(PagingData.empty())
    val headlineNews = _headlineNews


    private val _searchNews : MutableLiveData<Resource<NewsDataClass>> = MutableLiveData()
    var searchNews : LiveData<Resource<NewsDataClass>> = _searchNews

//    fun fetchFeaturedCars(): Flow<PagingData<Car>> {
//        val currentFeaturedCarsPagingFlow = featuredCarsPagingFlow
//        if (currentFeaturedCarsPagingFlow != null) return currentFeaturedCarsPagingFlow
//
//        val newFeaturedCarsPagingFlow = carsInventoryRepository
//            .getCarsInventoryListing(SelectedFilterValues().copy(featured = true), null)
//            .cachedIn(viewModelScope)
//        featuredCarsPagingFlow = newFeaturedCarsPagingFlow
//
//        return newFeaturedCarsPagingFlow
//    }


    fun getHeadlineNews(countryCode : String? = null): Flow<PagingData<NewsResponse>> {
        viewModelScope.launch {
            if (countryCode != null) {
                mainRepository.getHeadlineNews(countryCode = countryCode).cachedIn(viewModelScope).collect {
                    _headlineNews.value = it
                }
            }
        }

        return _headlineNews
    }

//    fun getHeadlineNews(countryCode : String){
//        viewModelScope.launch {
//            mainRepository.getHeadlineNews(countryCode = countryCode).cachedIn(viewModelScope).collect {
//                _headlineNews.value = it
//            }
//        }
//    }

    //Implement the function that now executes API call
//    private fun getHeadlineNews(countryCode: String){
//        viewModelScope.launch {
//            //Before making the network call, lets emit the loading state to live data
//            _breakingNews.postValue(Resource.Loading())
//            val breakingNewsResponse = repository.getHeadlineNews(countryCode, newsPagination)
//            _breakingNews.postValue(breakingNewsApiResponseHandler(breakingNewsResponse))
//        }
//    }

//    private fun breakingNewsApiResponseHandler(
//        response: Response<NewsDataClass>
//    ) : Resource<NewsDataClass>{
//        val responseBody = response.body()
//        if(response.isSuccessful && responseBody!=null){
//            //when response is successful, increase current page number
//                newsPagination++
//            //originally receivedBreakingNewsResponse will be null at first load
//            if (receivedBreakingNewsResponse == null){
//                receivedBreakingNewsResponse = responseBody
//            }
//            else{
//                val existingFetchedData = receivedBreakingNewsResponse?.articles
//                val newFetchedData = responseBody.articles
//                //At this point change from the generated data class property article List<Articles> to
//                //MutableList<Article>
//                //So the logic is make all ur checks, and finally add to existing list
//                existingFetchedData?.addAll(newFetchedData)
//            }
//            return Resource.Success(receivedBreakingNewsResponse ?: responseBody)
//        }
//        return Resource.Error(response.)
//    }

//    fun searchNewsInVM(searchQuery: String){
//        viewModelScope.launch {
//            //Before making the network call, lets emit the loading state to live data
//            _searchNews.postValue(Resource.Loading())
//            val searchNewsResponse = mainRepository.searchDataFromApiInTestRepository(searchQuery,searchNewsPagination)
//            _searchNews.postValue(searchNewsApiResponseHandler(searchNewsResponse))
//        }
//    }

//    private fun searchNewsApiResponseHandler(
//        response: Response<NewsDataClass>
//    ) : Resource<NewsDataClass>{
//        val responseBody = response.body()
//        if(response.isSuccessful && responseBody!=null){
//            //when response is successful, increase current page number
//            searchNewsPagination++
//            //originally receivedBreakingNewsResponse will be null at first load
//            if (receivedSearchNewsResponse  == null){
//                receivedSearchNewsResponse = responseBody
//            }
//            else{
//                val existingFetchedData = receivedSearchNewsResponse?.articles
//                val newFetchedData = responseBody.articles
//                //At this point change from the generated data class property article List<Articles> to
//                //MutableList<Article>
//                //So the logic is make all ur checks, and finally add to existing list
//                existingFetchedData?.addAll(newFetchedData)
//            }
//            return Resource.Success(receivedSearchNewsResponse ?: responseBody)
//        }
//        return Resource.Error(response.message())
//    }

    //upsert function
//    fun upsert(article: NewsDataClass.Article) = viewModelScope.launch {
//        mainRepository.upsert(article)
//    }
//
//    fun getDataFromDB() = mainRepository.getNewsFromDBInMainRepository()
//
//    fun deleteDataFromDB(article: NewsDataClass.Article) = viewModelScope.launch {
//        mainRepository.deleteNewsInDBFromRepo(article)
//    }
}