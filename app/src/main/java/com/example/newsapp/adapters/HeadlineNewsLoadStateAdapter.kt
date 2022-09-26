package com.example.newsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R

class HeadlineNewsLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoadStateViewHolder>(){
    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder =
        LoadStateViewHolder(parent, retry)


}

class LoadStateViewHolder(parent: ViewGroup, retry: () -> Unit) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.footer_load_state_view,parent,false)
) {
    private val footerProgressBar: ProgressBar = itemView.findViewById(R.id.footerProgressBar)
    private val footerErrorMsg: TextView = itemView.findViewById(R.id.footerErrorMsg)
    private val footerRetryBtn: Button = itemView.findViewById<Button>(R.id.footerRetryBtn)
        .also { it.setOnClickListener { retry.invoke() } }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            footerErrorMsg.text = loadState.error.localizedMessage
        }
        footerProgressBar.visibility = toVisibility(loadState is LoadState.Loading)
        footerRetryBtn.visibility = toVisibility(loadState !is LoadState.Loading)
        footerErrorMsg.visibility = toVisibility(loadState !is LoadState.Loading)
    }

    private fun toVisibility(constraint: Boolean): Int = if (constraint) {
        View.VISIBLE
    } else {
        View.GONE
    }

}
