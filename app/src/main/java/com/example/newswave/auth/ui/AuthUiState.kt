package com.example.newswave.auth.ui

import com.example.newswave.core.presentation.ui.utils.UiText

data class AuthUiState(
    val isNewUser: Boolean = false,
    val isLoading: Boolean = false,
    val generalMessage: UiText? = null,
    val isLoginSuccessful: Boolean = false,
)