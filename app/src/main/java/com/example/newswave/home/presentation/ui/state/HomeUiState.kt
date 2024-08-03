package com.example.newswave.home.presentation.ui.state

import androidx.paging.PagingData
import com.example.newswave.core.presentation.ui.state.NewsItemUiState
import com.example.newswave.core.presentation.ui.utils.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow

data class HomeUiState(
    val category: String = "",
    val isLoading: Boolean = false,
    val generalMessage: UiText? = null
)
