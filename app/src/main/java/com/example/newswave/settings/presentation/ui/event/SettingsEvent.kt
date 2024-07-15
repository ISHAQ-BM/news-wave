package com.example.newswave.settings.presentation.ui.event

sealed class SettingsEvent {
    data object ToggleDarkMode : SettingsEvent()
    data object LogOut : SettingsEvent()
}