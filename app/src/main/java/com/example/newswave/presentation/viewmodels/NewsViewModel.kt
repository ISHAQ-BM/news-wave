package com.example.newswave.presentation.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_ETHERNET
import android.net.ConnectivityManager.TYPE_MOBILE
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
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

    private val _searchNews= MutableLiveData<Resource<News>>()
    val searchNews: LiveData<Resource<News>> = _searchNews


    init {
        if (isNetworkAvailable())
            readNetworkState("top")
        else{
            _latestNews.postValue(Resource.Error("No internet connection"))
            readNetworkState("top")
        }
    }













    fun loadNewsData(category:String) =viewModelScope.launch {
        _latestNews.postValue(Resource.Loading())
        _latestNews.postValue(newsRepository.getLatestNews(category))
        }


    fun searchNews(searchQuery:String?) =viewModelScope.launch {
        _searchNews.postValue(Resource.Loading())
        _searchNews.postValue(newsRepository.searchNews(searchQuery))
    }






    fun bookmarkArticle(article: Article)=viewModelScope.launch (Dispatchers.IO){
        _latestNews.value?.data?.articles?.get(_latestNews.value?.data?.articles?.indexOf(article)!!)?.isBookmarked=true
        newsRepository.insertArticle(article)
    }

    fun getBookmarkedArticles()=newsRepository.getBookmarkedNews()

    fun deleteArticle(article: Article)=viewModelScope.launch (Dispatchers.IO){

        newsRepository.deleteArticle(article)
    }


    fun readNetworkState(category:String){
        val connectivityManager = getApplication<NewsWaveApp>()
            .getSystemService(ConnectivityManager::class.java)
        connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network : Network) {
                Log.e("TAG", "The default network is now: $network")
                loadNewsData(category)
            }

            override fun onLost(network : Network) {
                Log.e("TAG",
                    "The application no longer has a default network. The last default network was $network"
                )
                loadNewsData(category)
            }

        })

    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getApplication<NewsWaveApp>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities != null && (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    }


}