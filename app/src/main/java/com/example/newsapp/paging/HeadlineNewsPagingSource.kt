package com.example.newsapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.api.WebService
import com.example.newsapp.constants.Constants.PAGE_SIZE
import com.example.newsapp.data.ui.NewsResponse
import com.example.newsapp.utils.toNewsResponse

class HeadlineNewsPagingSource(
    private val webService: WebService,
    private val countryCode : String
) : PagingSource<Int, NewsResponse>() {

    override fun getRefreshKey(state: PagingState<Int, NewsResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsResponse> {
        val currentPage = params.key ?: 1
        return try {
            val response = webService.getHeadlineNews(countryCode = countryCode, page = currentPage, pageSize = PAGE_SIZE)
            val endOfPaginationReached = response.articles.isEmpty()

            if (response.articles.isNotEmpty()){
                LoadResult.Page(
                    data = response.articles.map { it.toNewsResponse() },
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if ( endOfPaginationReached == true) null else currentPage + 1
                )
            }else{
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        }catch (e : Exception){
            LoadResult.Error(e)
        }
    }
}