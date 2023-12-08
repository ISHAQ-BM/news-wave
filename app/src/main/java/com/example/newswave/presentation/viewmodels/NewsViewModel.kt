package com.example.newswave.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newswave.data.datasource.network.models.NewsDto
import com.example.newswave.data.mappers.toValidArticles
import com.example.newswave.domain.models.Article
import com.example.newswave.domain.repositories.NewsRepository
import com.example.newswave.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) :ViewModel() {

    private val _latestNews= MutableLiveData<Resource<NewsDto>>()
    val latestNews: LiveData<Resource<NewsDto>> = _latestNews

    var latestNewsPage=""

    var latestNewsResponse:NewsDto?=null








    init {
        loadNewsData()
    }

    private fun loadNewsData() =viewModelScope.launch {
        _latestNews.postValue(Resource.Loading())
        _latestNews.postValue(newsRepository.getLatestNews())

    }




    fun bookmarkArticle(article: Article)=viewModelScope.launch (Dispatchers.IO){
        _latestNews.value?.data?.toValidArticles()?.get(_latestNews.value?.data?.toValidArticles()?.indexOf(article)!!)?.isBookmarked = true
        Log.d("articles","$article")
        newsRepository.insertArticle(article)
    }

    fun getBookmarkedArticles()=newsRepository.getBookmarkedNews()

    fun deleteArticle(article: Article)=viewModelScope.launch (Dispatchers.IO){
        newsRepository.deleteArticle(article)
    }



}