package com.example.newswave.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newswave.data.datasource.network.models.NewsDto
import com.example.newswave.domain.mapper.toValidArticles
import com.example.newswave.domain.models.Article
import com.example.newswave.domain.repositories.NewsRepository
import com.example.newswave.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) :ViewModel() {

    private val _latestNews= MutableLiveData<Resource<NewsDto>>()
    val latestNews: LiveData<Resource<NewsDto>> = _latestNews

    private val _category = MutableLiveData<String>()
    val category:LiveData<String> = _category

    init {
        loadNewsData("top")
        Log.d("init block","hello")
    }













    private fun loadNewsData(category:String) =viewModelScope.launch {
        _latestNews.postValue(Resource.Loading())
        val response=async { newsRepository.getLatestNews(category) }
        _latestNews.postValue(response.await())
        Log.d("load news","${response.await().data?.toValidArticles()}")


    }




    fun bookmarkArticle(article: Article)=viewModelScope.launch (Dispatchers.IO){
        Log.d("articles","${_latestNews.value?.data?.toValidArticles()?.get(_latestNews.value?.data?.toValidArticles()?.indexOf(article)!!)}")
        _latestNews.value?.data?.toValidArticles()?.get(_latestNews.value?.data?.toValidArticles()?.indexOf(article)!!)?.isBookmarked =true
        Log.d("articles","${_latestNews.value?.data?.toValidArticles()?.get(_latestNews.value?.data?.toValidArticles()?.indexOf(article)!!)}")

        newsRepository.insertArticle(article)
    }

    fun getBookmarkedArticles()=newsRepository.getBookmarkedNews()

    fun deleteArticle(article: Article)=viewModelScope.launch (Dispatchers.IO){
        newsRepository.deleteArticle(article)
    }

    fun setCategory(shownCategory: String) {
        _category.value=shownCategory
        Log.d("shown category","${_category.value}")
        loadNewsData(shownCategory)
    }


}