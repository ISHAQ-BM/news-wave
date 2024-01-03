package com.example.newswave.domain.repositories

import androidx.lifecycle.LiveData
import com.example.newswave.domain.models.Article
import com.example.newswave.domain.models.News
import com.example.newswave.core.Resource

interface NewsRepository {
    suspend fun getLatestNews(category:String): Resource<News>
    suspend fun getLatestNewsByPage(category:String,page:String): Resource<News>
    suspend fun searchNews(searchQuery: String?): Resource<News>
    suspend fun searchNewsByPage(searchQuery: String,page:String): Resource<News>
    fun getBookmarkedNews(): LiveData<List<Article>>
    suspend fun insertArticle(article: Article)
    suspend fun deleteArticle(article: Article)

   }