package com.example.newswave.home.presentation

import com.example.newswave.core.presentation.ui.utils.UiText

data class HomeUiState(
    val isLoading: Boolean = false,
    val generalMessage: UiText? = null
)
