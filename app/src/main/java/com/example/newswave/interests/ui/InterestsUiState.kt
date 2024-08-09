package com.example.newswave.interests.ui

import com.example.newswave.core.presentation.ui.utils.UiText

data class InterestsUiState(
    val originalInterests: List<String> = listOf(),
    val modifiedInterests: List<String> = listOf(),
    val saveSuccessful: Boolean = false,
    val isLoading: Boolean = false,
    val generalMessage: UiText? = null,
)
