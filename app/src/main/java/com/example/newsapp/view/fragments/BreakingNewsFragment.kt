package com.example.newsapp.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentBreakingNewsBinding
import com.example.newsapp.viewmodel.MainViewModel


class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {
    private lateinit var binding: FragmentBreakingNewsBinding

    private val viewModel : MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBreakingNewsBinding.bind(view)

    }

}