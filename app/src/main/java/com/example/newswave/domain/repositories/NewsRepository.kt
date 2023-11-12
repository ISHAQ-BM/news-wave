package com.example.newswave.domain.repositories

import androidx.lifecycle.LiveData
import com.example.newswave.domain.models.Article
import com.example.newswave.utils.Resource

interface NewsRepository {
    suspend fun getLatestNews():Resource<List<Article>>
    fun searchNews(searchQuery: String):Resource<List<Article>>
    fun getSavedNews(): LiveData<List<Article>>
    suspend fun insertArticle(article: Article)
    suspend fun deleteArticle(article: Article)
}