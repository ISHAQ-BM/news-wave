package com.example.newswave.search.presentation.ui.state

import com.example.newswave.core.presentation.ui.utils.UiText
import com.example.newswave.news.presentation.ui.state.NewsItemUiState
import com.example.newswave.news.presentation.ui.state.NewsUiState

data class SearchUiState (

    val searchQuery:String="",
    val isLoading:Boolean=false,
    val searchResult:List<NewsItemUiState> = listOf(),
    val generalMessage: UiText? = null
)