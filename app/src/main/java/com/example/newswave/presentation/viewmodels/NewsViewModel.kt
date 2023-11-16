package com.example.newswave.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newswave.data.datasource.network.models.NewsDto
import com.example.newswave.domain.repositories.NewsRepository
import com.example.newswave.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) :ViewModel() {

    private val _latestNews= MutableLiveData<Resource<NewsDto>>()
    val latestNews: LiveData<Resource<NewsDto>> = _latestNews


}