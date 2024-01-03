package com.example.newswave.data.repositories

import androidx.lifecycle.LiveData
import com.example.newswave.data.datasource.local.NewsDao
import com.example.newswave.data.datasource.network.NewsApi
import com.example.newswave.data.mapper.NewsDtoMapper
import com.example.newswave.domain.models.Article
import com.example.newswave.domain.models.News
import com.example.newswave.domain.repositories.NewsRepository
import com.example.newswave.core.Resource
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor (
    private val api : NewsApi,
    private val newsDao: NewsDao,
    newsDtoMapper: NewsDtoMapper
): BaseRepository(newsDtoMapper),NewsRepository{
    override suspend fun getLatestNews(category:String): Resource<News> = safeApiCall { api.getLatestNews(category=category) }


    override suspend fun getLatestNewsByPage(category:String,page:String): Resource<News> = safeApiCall { api.getLatestNewsByPage(category = category,pageNumber = page) }


    override suspend fun searchNews(searchQuery: String?): Resource<News> = safeApiCall { api.searchForNews(searchQuery) }


    override suspend fun searchNewsByPage(searchQuery: String,page:String): Resource<News> = safeApiCall { api.searchForNewsByPage(searchQuery, pageNumber = page) }



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