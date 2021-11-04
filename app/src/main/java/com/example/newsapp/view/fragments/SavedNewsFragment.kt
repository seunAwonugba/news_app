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
import com.example.newsapp.databinding.FragmentSavedNewsBinding
import com.example.newsapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {
    private var _binding: FragmentSavedNewsBinding? = null
    private val binding get() = _binding!!

    private val viewModel : MainViewModel by viewModels()

    private lateinit var projectAdapter: ProjectAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSavedNewsBinding.bind(view)
        setUpRecyclerView()

        projectAdapter.setListItemClickListener {
            val passData = SavedNewsFragmentDirections.actionSavedNewsFragmentToArticleFragment(it)
            findNavController().navigate(passData)
        }
    }

    private fun setUpRecyclerView(){
        projectAdapter = ProjectAdapter()
        binding.saveNewsFragmentRV.apply {
            adapter = projectAdapter
            layoutManager = LinearLayoutManager(requireContext())
            binding.saveNewsFragmentRV.addItemDecoration(
                DividerItemDecoration(
                    binding.saveNewsFragmentRV.context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}