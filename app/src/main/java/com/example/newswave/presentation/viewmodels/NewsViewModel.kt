package com.example.newswave.presentation.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newswave.NewsWaveApp
import com.example.newswave.domain.models.Article
import com.example.newswave.domain.models.News
import com.example.newswave.domain.repositories.NewsRepository
import com.example.newswave.core.Resource
import com.example.newswave.news.presentation.ui.state.NewsItemUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    application: Application
) :AndroidViewModel(application) {

    private val _latestNews= MutableLiveData<Resource<List<NewsItemUiState>>>()
    val latestNews: LiveData<Resource<List<NewsItemUiState>>> = _latestNews
    private var latestNewsPage:String?=null
    var latestNewsResponse:News?=null

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
        newsRepository.getLatestNews(category).data?.articles?.map {

        }
        _latestNews.postValue(Resource.Success(newsRepository.getLatestNews(category).data?.articles!!.map {
            NewsItemUiState(
                id = it.link,
                author = it.creator,
                category = it.category,
                imageUrl = it.imageUrl,
                title = it.title,
                publishDate = it.pubTime,
                link = it.link
            )
        }))

    }

    fun loadMoreNews(category:String){
//        viewModelScope.launch {
//            if (_latestNews.value?.data?.nextPage != null)
//                latestNewsResponse = newsRepository.getLatestNewsByPage(
//                    category = category,
//                    _latestNews.value?.data?.nextPage!!
//                ).data
//            _latestNews.value?.data?.nextPage=latestNewsResponse?.nextPage
//            latestNewsResponse?.articles?.let { _latestNews.value?.data?.articles?.addAll(it) }
//
//        }
    }


    fun searchNews(searchQuery:String?) =viewModelScope.launch {
        _searchNews.postValue(Resource.Loading())
        _searchNews.postValue(newsRepository.searchNews(searchQuery))
    }






    fun bookmarkArticle(article: Article)=viewModelScope.launch (Dispatchers.IO){
//        _latestNews.value?.data?.articles?.get(_latestNews.value?.data?.articles?.indexOf(article)!!)?.isBookmarked=true
//        newsRepository.insertArticle(article)
    }

    fun getBookmarkedArticles()=newsRepository.getBookmarkedNews()

    fun deleteArticle(article: Article)=viewModelScope.launch (Dispatchers.IO){
//        if (_latestNews.value?.data?.articles?.indexOf(article) != -1){
//            _latestNews.value?.data?.articles?.get(_latestNews.value?.data?.articles?.indexOf(article)!!)?.isBookmarked=false
//        }
//        newsRepository.deleteArticle(article)
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