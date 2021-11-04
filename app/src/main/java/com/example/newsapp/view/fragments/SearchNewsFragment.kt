package com.example.newsapp.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapters.ProjectAdapter
import com.example.newsapp.constants.Constants.SEARCH_DELAY
import com.example.newsapp.databinding.FragmentSearchNewsBinding
import com.example.newsapp.utils.ApiCallErrorHandler
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
        fetchBreakingNews()

        projectAdapter.setListItemClickListener {
            val passData = SearchNewsFragmentDirections.actionSearchNewsFragmentToArticleFragment(it)
            findNavController().navigate(passData)
        }

        //implement search to search
        var job : Job? = null
        binding.searchFieldId.addTextChangedListener {editable->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_DELAY)
                editable?.let {
                    if(editable.toString().isNotEmpty()){
                        viewModel.searchNewsInVM(editable.toString())
                    }
                }
            }
        }

        viewModel.searchNews.observe(
            viewLifecycleOwner, Observer { response ->
                when(response){
                    is ApiCallErrorHandler.Success -> {
                        hideProgressBar()
                        response.data?.articles?.let {
                            projectAdapter.news = it
                        }
                    }

                    is ApiCallErrorHandler.Error -> {
                        hideProgressBar()
                        response.message?.let {
                            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                        }
                    }

                    is ApiCallErrorHandler.Loading -> {
                        displayProgressBar()

                    }
                }
            }
        )
    }

    private fun fetchBreakingNews(){
        viewModel.breakingNews.observe(
            viewLifecycleOwner, Observer { response ->
                when(response){
                    is ApiCallErrorHandler.Success -> {
                        hideProgressBar()
                        response.data?.articles?.let {
                            projectAdapter.news = it
                        }
                    }

                    is ApiCallErrorHandler.Error -> {
                        hideProgressBar()
                        response.message?.let {
                            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                        }
                    }

                    is ApiCallErrorHandler.Loading -> {
                        displayProgressBar()

                    }
                }
            }
        )
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
        }
    }

    private fun displayProgressBar(){
        binding.searchFragmentProgressBarId.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        binding.searchFragmentProgressBarId.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}