package com.example.newswave.search.presentation.ui.state

import com.example.newswave.core.presentation.ui.utils.UiText
import com.example.newswave.core.presentation.ui.state.NewsItemUiState

data class SearchUiState (

    val searchQuery:String="",
    val isLoading:Boolean=false,
    val generalMessage: UiText? = null
)