package com.example.newswave.presentation.adapters

import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.newswave.R
import com.example.newswave.databinding.NewsListItemBinding
import com.example.newswave.domain.models.Article

class NewsAdapter : ListAdapter<Article, NewsAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            NewsListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(article) }
        }
    }


    inner class ViewHolder(private val binding: NewsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article?) {
            binding.newsTitle.text = article?.title
            "by ${article?.creator}".also { binding.author.text = it }
            binding.publishedTime.text = article?.pubTime
            binding.category.text = article?.category
            val imgUri = article?.imageUrl?.toUri()?.buildUpon()?.scheme("https")?.build()
            binding.image.load(imgUri) {
                placeholder(R.drawable.loading_animation)
            }

        }


    }

    object DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title ==newItem.title
        }

    }

    private var onItemClickListener :((Article) -> Unit )? = null

    fun setOnItemClickListener(listener : (Article) -> Unit ){
        onItemClickListener =listener
    }
}