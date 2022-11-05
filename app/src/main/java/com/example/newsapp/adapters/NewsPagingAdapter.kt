package com.example.newsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.newsapp.R
import com.example.newsapp.data.ui.NewsResponse
import com.example.newsapp.databinding.ListItemBinding

class NewsPagingAdapter constructor(
    val onItemClick: (newsResponse : NewsResponse) -> Unit
) : PagingDataAdapter<NewsResponse, NewsPagingAdapter.AppViewHolder>(DiffUtilCallBack()) {


    inner class AppViewHolder(
        private val binding : ListItemBinding
    ) : RecyclerView.ViewHolder(binding.root){

        private lateinit var newsResponse: NewsResponse

        init {
            binding.root.setOnClickListener {
                onItemClick(newsResponse)
            }
        }

        fun bind(data : NewsResponse){
            newsResponse = data
            binding.imageId.load(data.image)
            binding.titleId.text = data.title
            binding.contentId.text = data.content
            binding.sourceId.text = data.sourceName
            binding.publishedAtId.text = data.publishedAt

        }

    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<NewsResponse>(){
        override fun areItemsTheSame(oldItem: NewsResponse, newItem: NewsResponse): Boolean {
            return oldItem.articleId == newItem.articleId
        }

        override fun areContentsTheSame(oldItem: NewsResponse, newItem: NewsResponse): Boolean {
            return oldItem.articleId == newItem.articleId
        }

    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val binding = ListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return AppViewHolder(binding)
    }
}