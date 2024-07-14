package com.example.newswave.news.presentation.ui.state

import com.example.newswave.core.presentation.ui.utils.UiText


data class NewsUiState(
    val articles: List<NewsItemUiState> = emptyList(),
    val category: String = "",
    val isLoading: Boolean = false,
    val generalMessage: UiText? = null
)