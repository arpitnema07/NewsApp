package com.example.news.utils


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.news.databinding.ItemNewsCardBinding
import com.example.news.network.Article


private val differCallback = object : DiffUtil.ItemCallback<Article>(){
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}

class NewsAdapter : ListAdapter<Article, NewsAdapter.ViewHolder>(differCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder private constructor(private val binding: ItemNewsCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            Glide.with(binding.root.context).load(article.urlToImage).into(binding.newsImg)
            binding.newsTitle.text = article.title
            binding.newsDescription.text = article.description
            Log.d("Adapter", "bind: ")
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val binding = ItemNewsCardBinding.inflate(LayoutInflater.from(parent.context))
                return ViewHolder(binding)
            }
        }
    }
}