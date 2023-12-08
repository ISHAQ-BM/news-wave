package com.example.newswave.domain.repositories

import androidx.lifecycle.LiveData
import com.example.newswave.data.datasource.network.models.NewsDto
import com.example.newswave.domain.models.Article
import com.example.newswave.domain.utils.Resource

interface NewsRepository {
    suspend fun getLatestNews(): Resource<NewsDto>
    suspend fun getLatestNewsByPage(page:String): Resource<NewsDto>
    suspend fun searchNews(searchQuery: String): Resource<NewsDto>
    suspend fun searchNewsByPage(searchQuery: String,page:String): Resource<NewsDto>
    fun getBookmarkedNews(): LiveData<List<Article>>
    suspend fun insertArticle(article: Article)
    suspend fun deleteArticle(article: Article)

   }