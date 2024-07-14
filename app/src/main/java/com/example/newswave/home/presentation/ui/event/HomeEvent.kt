package com.example.newswave.home.presentation.ui.event



sealed class HomeEvent {
    data class SearchQueryChanged(val query: String) : HomeEvent()
    data class TabSelected(val category: String) : HomeEvent()
}