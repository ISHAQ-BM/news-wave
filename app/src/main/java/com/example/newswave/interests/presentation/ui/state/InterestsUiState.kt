package com.example.newswave.interests.presentation.ui.state

import androidx.compose.runtime.mutableStateOf
import com.example.newswave.core.presentation.ui.utils.UiText

data class InterestsUiState(
    val previousInterests:List<String> = listOf(),
    val interestsList:List<String> = listOf(),
    val updateSuccessful:Boolean =false,
    val isLoading: Boolean = false,
    val generalMessage: UiText? = null,
)
