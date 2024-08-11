package com.example.newswave.bookmark.ui

import com.example.newswave.core.presentation.ui.state.NewsItemUiState
import com.example.newswave.core.presentation.ui.utils.UiText

data class BookmarkUiState (
    val bookmarkNewsList: List<NewsItemUiState> = emptyList(),
    val isLoading: Boolean = false,
    val generalMessage: UiText? = null
)