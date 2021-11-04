package com.example.newsapp.view.fragments

import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebView.RENDERER_PRIORITY_BOUND
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentArticleBinding
import com.example.newsapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleFragment : Fragment(R.layout.fragment_article) {
    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    private val viewModel : MainViewModel by viewModels()

    //receive dara passed using args from the 3 fragments
    private val instanceOfPassedArgs : ArticleFragmentArgs by navArgs()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentArticleBinding.bind(view)

        val passedArgs = instanceOfPassedArgs.safeArgsIntent

        binding.webViewId.apply {
            webViewClient = WebViewClient()
            passedArgs.url?.let { loadUrl(it) }
            settings.javaScriptEnabled = true
            setRendererPriorityPolicy(RENDERER_PRIORITY_BOUND, true)
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}