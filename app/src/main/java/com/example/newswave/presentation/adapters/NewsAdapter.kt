package com.example.newswave.presentation.adapters

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.newswave.R
import com.example.newswave.databinding.NewsListItemBinding
import com.example.newswave.domain.models.Article

class NewsAdapter(
    private val onItemClick: (Article) -> Unit,
    private var optionsMenuClickListener: OptionsMenuClickListener


) : ListAdapter<Article, NewsAdapter.ViewHolder>(DiffCallback) {


    interface OptionsMenuClickListener {
        fun onOptionsMenuClicked(article: Article, view: View)
    }

    inner class ViewHolder(
        private val binding: NewsListItemBinding,
        onItemClicked: (Int) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                onItemClicked(adapterPosition)
            }
            binding.dropdownMenu.setOnClickListener {
                optionsMenuClickListener.onOptionsMenuClicked(getItem(adapterPosition),it)

            }
        }

        fun bind(article: Article?) {
            binding.newsTitle.text = article?.title
            "by ${article?.creator}".also { binding.author.text = it }
            binding.publishedTime.text = article?.pubTime
            binding.category.text = article?.category
            val imgUri = article?.imageUrl?.toUri()?.buildUpon()?.scheme("https")?.build()
            binding.image.load(imgUri) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
            }

        }


    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            NewsListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ){
            onItemClick(getItem(it))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }




    object DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title ==newItem.title
        }

    }
}

