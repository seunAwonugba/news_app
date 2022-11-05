package com.example.newsapp.view.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapters.HeadlineNewsLoadStateAdapter
import com.example.newsapp.adapters.NewsPagingAdapter
import com.example.newsapp.data.ui.NewsResponse
import com.example.newsapp.databinding.FragmentHeadlineNewsBinding
import com.example.newsapp.utils.navController
import com.example.newsapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HeadlineNewsFragment : Fragment(R.layout.fragment_headline_news) {
    private var _binding: FragmentHeadlineNewsBinding? = null
    private val binding get() = _binding!!

    private val viewModel : MainViewModel by viewModels()

    private val newsPagingAdapter by lazy { NewsPagingAdapter(this::handleListItemClick) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHeadlineNewsBinding.bind(view)
        pagingState()
        getHeadlineNews()
        val selectedCountry = binding.countryPicker.selectedCountryCode
    }

    private fun pagingState(){
        newsPagingAdapter.addLoadStateListener { combinedLoadStates ->

            val errorState =
                when (combinedLoadStates.refresh) { is LoadState.Error -> {
                    combinedLoadStates.refresh as LoadState.Error
                }
                else -> null
            }
            if (errorState != null) {
                binding.initialLoadErrorText.text = errorState.error.message
            }

            binding.apply {
                initialProgressBar.isVisible = combinedLoadStates.source.refresh is LoadState.Loading
                headLineNewsRv.isVisible = combinedLoadStates.source.refresh is LoadState.NotLoading
                binding.initialLoadErrorText.isVisible = combinedLoadStates.source.refresh is LoadState.Error
                binding.initialLoadErrorRetryBtn.isVisible = combinedLoadStates.source.refresh is LoadState.Error

                //empty state
                if (combinedLoadStates.source.refresh is LoadState.NotLoading &&
                        combinedLoadStates.append.endOfPaginationReached &&
                        newsPagingAdapter.itemCount < 1
                ){
                    headLineNewsRv.isVisible = false
                    emptyStateTextView.isVisible = true
                }else{
                    emptyStateTextView.isVisible = false
                }
            }
        }

        binding.headLineNewsRv.apply {
            adapter = newsPagingAdapter.withLoadStateFooter(
                footer = HeadlineNewsLoadStateAdapter{newsPagingAdapter.retry()}
            )
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(
                DividerItemDecoration(
                    binding.headLineNewsRv.context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        binding.initialLoadErrorRetryBtn.setOnClickListener {
            newsPagingAdapter.retry()
        }
    }

    private fun getHeadlineNews() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getHeadlineNews(binding.countryPicker.selectedCountryNameCode).collectLatest {
                newsPagingAdapter.submitData(it)
            }
        }
    }

    private fun handleListItemClick(newsResponse: NewsResponse) {
        val safeArgs = HeadlineNewsFragmentDirections.headlineToArticle(newsResponse.articleId.toString())
        navController.navigate(safeArgs)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}