package com.example.newswave.data.repositories

import androidx.lifecycle.LiveData
import com.example.newswave.data.datasource.local.NewsDao
import com.example.newswave.data.datasource.network.NewsApi
import com.example.newswave.data.mappers.toValidArticles
import com.example.newswave.domain.models.Article
import com.example.newswave.domain.repositories.NewsRepository
import com.example.newswave.domain.utils.Resource
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor (
    private val api : NewsApi,
    private val newsDao: NewsDao
): NewsRepository {
    override suspend fun getLatestNews(): Resource<List<Article>> {
        return try {
            Resource.Success(
                data = api.getLatestNews().toValidArticles()
            )
        }catch (e:Exception){
            Resource.Error(e.message ?: "Error")
        }
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