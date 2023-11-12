package com.example.newswave.data.repositories

import androidx.lifecycle.LiveData
import com.example.newswave.data.datasource.local.NewsDao
import com.example.newswave.data.datasource.network.NewsApi
import com.example.newswave.domain.models.Article
import com.example.newswave.domain.repositories.NewsRepository
import com.example.newswave.utils.Resource
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor (
    private val api : NewsApi,
    private val newsDao: NewsDao
): NewsRepository {
    override suspend fun getLatestNews(): Resource<List<Article>> {
        TODO("Not yet implemented")
    }

    override fun searchNews(searchQuery: String): Resource<List<Article>> {
        TODO("Not yet implemented")
    }

    override fun getSavedNews(): LiveData<List<Article>> {
        return newsDao.getNews()
    }

    override suspend fun insertArticle(article: Article) {
        newsDao.insert(article)
    }

    override suspend fun deleteArticle(article: Article) {
        newsDao.delete(article)
    }


}