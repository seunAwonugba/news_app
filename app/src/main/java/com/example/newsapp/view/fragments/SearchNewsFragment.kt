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
    private lateinit var binding: FragmentSearchNewsBinding

    private val viewModel : MainViewModel by viewModels()

    private lateinit var projectAdapter: ProjectAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchNewsBinding.bind(view)
        setUpRecyclerView()

        projectAdapter.setListItemClickListener {
            findNavController().navigate(
                R.id.action_searchNewsFragment_to_articleFragment
            )
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
                Log.d("TAG", "View Model Success : ${response.data?.articles}")
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
}