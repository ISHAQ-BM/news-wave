package com.example.newswave.bookmark.presentation.ui.state

import com.example.newswave.core.presentation.ui.utils.UiText
import com.example.newswave.news.presentation.ui.state.NewsItemUiState

data class BookmarkUiState (
    val bookmarkedNews: List<NewsItemUiState> = emptyList(),
    val isLoading: Boolean = false,
    val generalMessage: UiText? = null
)