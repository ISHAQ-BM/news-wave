package com.example.newswave.search.presentation.ui.event

sealed class SearchNewsEvent {
    data class ToggleSearch(val searchQuery:String):SearchNewsEvent()
}