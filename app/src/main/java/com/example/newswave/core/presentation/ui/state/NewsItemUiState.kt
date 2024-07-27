package com.example.newswave.core.presentation.ui.state

data class NewsItemUiState(
    val id: String,
    val title: String,
    val author: String?,
    val imageUrl: String,
    val publishDate: String,
    val category: String,
    val link:String,
    val isBookmarked:Boolean
)
