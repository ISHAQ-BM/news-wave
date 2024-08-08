package com.example.newswave.auth.presentation.ui.state

import com.example.newswave.core.presentation.ui.utils.UiText

data class SignUserUiState (
    val isNewUser:Boolean =false,
    val isLoading: Boolean = false,
    val generalMessage: UiText? = null,
    val isLoginSuccessful: Boolean = false,
)