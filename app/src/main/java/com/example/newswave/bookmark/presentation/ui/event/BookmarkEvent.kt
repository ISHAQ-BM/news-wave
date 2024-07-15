package com.example.newswave.bookmark.presentation.ui.event

import com.example.newswave.core.domain.model.News


sealed class BookmarkEvent {
    data object LoadBookmark : BookmarkEvent()
    data class UnBookmark(val news: News):BookmarkEvent()
}