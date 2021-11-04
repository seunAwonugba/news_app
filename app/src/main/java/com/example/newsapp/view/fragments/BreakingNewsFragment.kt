package com.example.newsapp.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapters.ProjectAdapter
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
                    else -> {
                        displayProgressBar()
                    }
                }
            }
        )

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
        }
    }

    private fun displayProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        binding.progressBar.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}