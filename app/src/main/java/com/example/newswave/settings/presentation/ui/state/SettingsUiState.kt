package com.example.newswave.settings.presentation.ui.state

import com.example.newswave.core.presentation.ui.utils.UiText

data class SettingsUiState(
    val isSignOut:Boolean =false,
    val isLoading: Boolean = false,
    val generalMessage: UiText? = null,
)
