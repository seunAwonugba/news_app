package com.example.newsapp.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsapp.data.ui.NewsResponse
import com.example.newsapp.di.NetworkModule
import com.example.newsapp.repository.MainRepository
import com.example.newsapp.utils.Resource
import com.example.newsapp.utils.updateValue
import com.example.newsapp.utils.wrapAsResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private var mainRepository : MainRepository
) : ViewModel() {


    private val headlineNews = MutableStateFlow<PagingData<NewsResponse>>(PagingData.empty())

    private val _state: MutableStateFlow<State> = MutableStateFlow(State())
    val state: StateFlow<State> = _state

    init {
        fetchUserLoans("arsenal")
    }

    fun getHeadlineNews(countryCode : String? = null): Flow<PagingData<NewsResponse>> {
        viewModelScope.launch {
            if (countryCode != null) {
                mainRepository.getHeadlineNews(countryCode = countryCode).cachedIn(viewModelScope).collect {
                    headlineNews.value = it
                }
            }
        }

        return headlineNews
    }

    private fun fetchUserLoans(query : String) {
        mainRepository
            .searchNews(query = query)
            .catch {
                when (it) {
                    is NetworkModule.ErrorInterceptor.ServerErrorException -> emit(listOf())
                    else -> throw it
                }
            }
            .wrapAsResource()
            .onEach {
                _state.updateValue { copy(searchNewsRes = it) }
            }
            .launchIn(viewModelScope)
    }

    data class State(val searchNewsRes: Resource<List<NewsResponse>> = Resource.initial())



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