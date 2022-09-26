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

class NewsPagingAdapter : PagingDataAdapter<NewsResponse, NewsPagingAdapter.BreakingNewsViewHolder>(DiffUtilCallBack()) {


    class BreakingNewsViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val image : ImageView = view.findViewById(R.id.imageId)
        private val title : TextView = view.findViewById(R.id.titleId)
        private val contents : TextView = view.findViewById(R.id.contentId)
        private val source : TextView = view.findViewById(R.id.sourceId)
        private val publishedAt : TextView = view.findViewById(R.id.publishedAtId)

        fun bind(data : NewsResponse){
            image.load(data.image)
            title.text = data.title
            contents.text = data.content
            source.text = data.sourceName
            publishedAt.text = data.publishedAt


        }

    }

    override fun onBindViewHolder(holder: BreakingNewsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreakingNewsViewHolder {
        val inflater = LayoutInflater.from(
            parent.context
        ).inflate(
            R.layout.list_item,
            parent,
            false
        )

        return BreakingNewsViewHolder(inflater)

    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<NewsResponse>(){
        override fun areItemsTheSame(oldItem: NewsResponse, newItem: NewsResponse): Boolean {
            return oldItem.articleId == newItem.articleId
        }

        override fun areContentsTheSame(oldItem: NewsResponse, newItem: NewsResponse): Boolean {
            return oldItem.articleId == newItem.articleId
        }

    }
}