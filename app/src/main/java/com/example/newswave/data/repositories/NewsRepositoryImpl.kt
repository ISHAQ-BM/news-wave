package com.example.newswave.data.repositories

import androidx.lifecycle.LiveData
import com.example.newswave.data.datasource.local.NewsDao
import com.example.newswave.data.datasource.network.NewsApi
import com.example.newswave.data.datasource.network.models.NewsDto
import com.example.newswave.domain.models.Article
import com.example.newswave.domain.repositories.NewsRepository
import com.example.newswave.domain.utils.Resource
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor (
    private val api : NewsApi,
    private val newsDao: NewsDao
): BaseRepository(),NewsRepository{
    override suspend fun getLatestNews(): Resource<NewsDto> = safeApiCall { api.getLatestNews() }


    override suspend fun getLatestNewsByPage(page:String): Resource<NewsDto> = safeApiCall { api.getLatestNewsByPage(pageNumber = page) }


    override suspend fun searchNews(searchQuery: String): Resource<NewsDto>  = safeApiCall { api.searchForNews(searchQuery) }


    override suspend fun searchNewsByPage(searchQuery: String,page:String): Resource<NewsDto> = safeApiCall { api.searchForNewsByPage(searchQuery, pageNumber = page) }



    override fun getBookmarkedNews(): LiveData<List<Article>> {
        return newsDao.getNews()
    }

    override suspend fun insertArticle(article: Article) {
        newsDao.insert(article)
    }

    override suspend fun deleteArticle(article: Article) {
        newsDao.delete(article)
    }



}