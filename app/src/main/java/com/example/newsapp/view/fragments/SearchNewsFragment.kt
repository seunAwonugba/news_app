package com.example.newsapp.view.fragments

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapters.ProjectAdapter
import com.example.newsapp.constants.Constants.PAGE_SIZE
import com.example.newsapp.constants.Constants.SEARCH_DELAY
import com.example.newsapp.databinding.FragmentSearchNewsBinding
import com.example.newsapp.utils.Resource
import com.example.newsapp.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {
    private var _binding: FragmentSearchNewsBinding? = null
    private val binding get() = _binding!!


    private val viewModel : MainViewModel by viewModels()

    private lateinit var projectAdapter: ProjectAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchNewsBinding.bind(view)
        setUpRecyclerView()
//        fetchBreakingNews()

//        projectAdapter.setListItemClickListener {
//            val passData = SearchNewsFragmentDirections.actionSearchNewsFragmentToArticleFragment(it)
//            findNavController().navigate(passData)
//        }

        //implement search to search
//        var job : Job? = null
//        binding.searchFieldId.addTextChangedListener {editable->
//            job?.cancel()
//            job = MainScope().launch {
//                delay(SEARCH_DELAY)
//                editable?.let {
//                    if(editable.toString().isNotEmpty()){
//                        viewModel.searchNewsInVM(editable.toString())
//                    }
//                }
//            }
//        }

//        viewModel.searchNews.observe(
//            viewLifecycleOwner
//        ) { response ->
//            when (response) {
//                is Resource.Success -> {
//                    hideProgressBar()
//                    response.data?.let {
//                        projectAdapter.news = it.articles.toList()
//                        val totalPages = it.totalResults / PAGE_SIZE + 2
//                        isLastPage = viewModel.searchNewsPagination == totalPages
//                    }
//                }
//
//                is Resource.Error -> {
//                    hideProgressBar()
//                    Snackbar.make(binding.root, response.message?.message.toString(), Snackbar.LENGTH_LONG).show()
//                }
//
//                is Resource.Loading -> {
//                    displayProgressBar()
//
//                }
//            }
//        }
    }

//    private fun fetchBreakingNews(){
//        viewModel.headlineNews.observe(
//            viewLifecycleOwner
//        ) { response ->
//            when (response) {
//                is Resource.Success -> {
//                    hideProgressBar()
//                    response.data?.let {
//                        projectAdapter.news = it.articles.toList()
//                        val totalPages = it.totalResults / PAGE_SIZE + 2
//                        isLastPage = viewModel.newsPagination == totalPages
//
//                    }
//                }
//
//                is Resource.Error -> {
//                    hideProgressBar()
//                    Snackbar.make(binding.root, response.message?.message.toString(), Snackbar.LENGTH_LONG).show()
//                }
//
//                is Resource.Loading -> {
//                    displayProgressBar()
//
//                }
//            }
//        }
//    }

    //implement pagination
    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    //manually implement members
    private val scrollListener = object  : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }

//        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//            super.onScrolled(recyclerView, dx, dy)
//            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
//            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
//            val visibleItemCount = layoutManager.childCount
//            val totalItemCount = layoutManager.itemCount
//
//            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
//            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
//            val isNotAtBeginning = firstVisibleItemPosition >= 0
//            // always get default response from api
//            val isTotalMoreThanVisible = totalItemCount >= PAGE_SIZE
//            val shouldPaginate =
//                isNotLoadingAndNotLastPage &&
//                        isAtLastItem &&
//                        isNotAtBeginning &&
//                        isTotalMoreThanVisible &&
//                        isScrolling
//            if(shouldPaginate) {
//                viewModel.searchNewsInVM(binding.searchFieldId.text.toString())
//                isScrolling = false
//            }
//        }
    }

    private fun setUpRecyclerView(){
        projectAdapter = ProjectAdapter()
        binding.searchFragmentRV.apply {
            adapter = projectAdapter
            layoutManager = LinearLayoutManager(requireContext())
            binding.searchFragmentRV.addItemDecoration(
                DividerItemDecoration(
                    binding.searchFragmentRV.context,
                    DividerItemDecoration.VERTICAL
                )
            )
            addOnScrollListener(this@SearchNewsFragment.scrollListener)
        }
    }

    private fun displayProgressBar(){
        binding.searchFragmentProgressBarId.visibility = View.VISIBLE
        isLoading = true
    }

    private fun hideProgressBar(){
        binding.searchFragmentProgressBarId.visibility = View.GONE
        isLoading = false
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}