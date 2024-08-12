package com.example.newswave.search.ui

import com.example.newswave.core.presentation.ui.state.NewsItemUiState
import com.example.newswave.core.presentation.ui.utils.UiText

data class SearchUiState (
    val latestNews:List<NewsItemUiState> = emptyList(),
    val isLoading:Boolean=false,
    val generalMessage: UiText? = null
)