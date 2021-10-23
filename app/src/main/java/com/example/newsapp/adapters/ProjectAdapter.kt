package com.example.newsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.dataclass.Article


//Diff util recyclerview without view binding because i need just one recyclerview adapter

class ProjectAdapter : RecyclerView.Adapter<ProjectAdapter.MyViewHolder>() {
    inner class MyViewHolder(var itemView2 : View) : RecyclerView.ViewHolder(itemView2){
        var newsImageView : ImageView
        var titleTextView : TextView
        var content : TextView
        var source : TextView
        var publishedDate : TextView

        init {
            newsImageView = itemView.findViewById(R.id.newsImageViewId)
            titleTextView = itemView.findViewById(R.id.newsTitleTextViewId)
            content = itemView.findViewById(R.id.newsContentTextViewId)
            source = itemView.findViewById(R.id.sourceTextViewId)
            publishedDate = itemView.findViewById(R.id.newsPublishedDateTextViewId)
        }
    }

    private val diffCallBack = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            //Note in the Article dataclass, by default ID did not come with it we created it
            //so the most unique item in the list will be URL

            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return newItem == oldItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)
    var news : List<Article>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.list_item, parent, false
        ))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentNews = news[position]

        holder.itemView2.apply {
            Glide.with(this)
                .load(currentNews.urlToImage)
                .into(holder.newsImageView)
        }
        holder.titleTextView.text = currentNews.title
        holder.publishedDate.text = currentNews.publishedAt
        holder.source.text = currentNews.source.name
        holder.content.text = currentNews.content

        holder.itemView.setOnClickListener {
            listItemClickListener?.let {
                it(currentNews)
            }
        }
    }

    override fun getItemCount(): Int {
        return news.size
    }

    //Implement onclick listeners
    private var listItemClickListener : ((Article) -> Unit)? = null

    fun setListItemClickListener(listener: (Article) -> Unit){
        listItemClickListener = listener
    }
}