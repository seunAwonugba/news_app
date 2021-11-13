package com.example.newsapp.view.fragments

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapters.ProjectAdapter
import com.example.newsapp.constants.Constants.QUERY_PAGE_SIZE
import com.example.newsapp.databinding.FragmentBreakingNewsBinding
import com.example.newsapp.utils.ApiCallErrorHandler
import com.example.newsapp.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {
    private var _binding: FragmentBreakingNewsBinding? = null
    private val binding get() = _binding!!

    private val viewModel : MainViewModel by viewModels()

    private lateinit var projectAdapter: ProjectAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentBreakingNewsBinding.bind(view)
        setUpRecyclerView()

        projectAdapter.setListItemClickListener {
            val passData = BreakingNewsFragmentDirections.actionBreakingNewsFragmentToArticleFragment(it)
            findNavController().navigate(passData)
        }

        viewModel.breakingNews.observe(
            viewLifecycleOwner, { response ->
                when(response){
                    is ApiCallErrorHandler.Success -> {
                        hideProgressBar()
                        response.data?.let {
                            projectAdapter.news = it.articles.toList()
                            val totalPages = it.totalResults / QUERY_PAGE_SIZE + 2
                            isLastPage = viewModel.newsPagination == totalPages
                            if(isLastPage){
                                binding.breakingNewsRVId.setPadding(0,0,0,0)
                            }
                            Snackbar.make(
                                binding.root,
                                response.message.toString(),
                                Snackbar.LENGTH_LONG
                            ).show()

                        }
                    }

                    is ApiCallErrorHandler.Error -> {
                        hideProgressBar()
                        response.message?.let {
                            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                            if(it == "timeout") Snackbar.make(binding.root, "Slow network, please refresh", Snackbar.LENGTH_LONG).show()
                            if(it == "Unable to resolve host \"newsapi.org\": No address associated with hostname") Snackbar.make(binding.root, "Please check your network connection and refresh", Snackbar.LENGTH_LONG).show()
                        }
                    }

                    is ApiCallErrorHandler.Loading -> {
                        displayProgressBar()

                    }
                    else -> {
                        displayProgressBar()
                    }
                }
            }
        )

    }

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

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            // always get default response from api
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate =
                isNotLoadingAndNotLastPage &&
                        isAtLastItem &&
                        isNotAtBeginning &&
                        isTotalMoreThanVisible &&
                        isScrolling
            if(shouldPaginate) {
                viewModel.getBreakingNewsInVM("us")
                isScrolling = false
            }
        }
    }

    private fun setUpRecyclerView(){
        projectAdapter = ProjectAdapter()
        binding.breakingNewsRVId.apply {
            adapter = projectAdapter
            layoutManager = LinearLayoutManager(requireContext())
            binding.breakingNewsRVId.addItemDecoration(
                DividerItemDecoration(
                    binding.breakingNewsRVId.context,
                    DividerItemDecoration.VERTICAL
                )
            )
            addOnScrollListener(this@BreakingNewsFragment.scrollListener)

        }
    }

    private fun displayProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
        isLoading = true
    }

    private fun hideProgressBar(){
        binding.progressBar.visibility = View.GONE
        isLoading = false
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}