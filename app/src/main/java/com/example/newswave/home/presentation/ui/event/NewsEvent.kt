package com.example.newswave.home.presentation.ui.event


sealed class NewsEvent {
    data class ArticleSelected(val articleId: String) : NewsEvent()
    //data object LoadNews : NewsEvent()
    data class CategoryChanged(val category:String): NewsEvent()
    data object LoadBookmarkedArticles : NewsEvent()
    data class BookmarkArticle(val articleId: String) : NewsEvent()
    data class UnBookmarkArticle(val articleId: String) : NewsEvent()
}