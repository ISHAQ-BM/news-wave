package com.example.newswave.presentation.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_ETHERNET
import android.net.ConnectivityManager.TYPE_MOBILE
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newswave.NewsWaveApp
import com.example.newswave.data.datasource.network.models.NewsDto
import com.example.newswave.data.mapper.toValidArticles
import com.example.newswave.domain.models.Article
import com.example.newswave.domain.models.News
import com.example.newswave.domain.repositories.NewsRepository
import com.example.newswave.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    application: Application
) :AndroidViewModel(application) {

    private val _latestNews= MutableLiveData<Resource<News>>()
    val latestNews: LiveData<Resource<News>> = _latestNews

    private val _category = MutableLiveData<String>()
    val category:LiveData<String> = _category

    init {
        loadNewsData("top")
        Log.d("init block","hello")
    }













    private fun loadNewsData(category:String) =viewModelScope.launch {
        _latestNews.postValue(Resource.Loading())
        if (isInternetAvailable()){
            _latestNews.postValue(newsRepository.getLatestNews(category))
        }else{
            _latestNews.postValue(Resource.Error("No internet connection"))
        }

    }




    fun bookmarkArticle(article: Article)=viewModelScope.launch (Dispatchers.IO){
        _latestNews.value?.data?.articles?.get(_latestNews.value?.data?.articles?.indexOf(article)!!)?.isBookmarked=true
        newsRepository.insertArticle(article)
    }

    fun getBookmarkedArticles()=newsRepository.getBookmarkedNews()

    fun deleteArticle(article: Article)=viewModelScope.launch (Dispatchers.IO){
        newsRepository.deleteArticle(article)
    }

    fun setCategory(shownCategory: String) {
        _category.value=shownCategory
        loadNewsData(shownCategory)
    }

    private fun isInternetAvailable():Boolean{
        val connectivityManager = getApplication<NewsWaveApp>()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }else{
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false

    }


}