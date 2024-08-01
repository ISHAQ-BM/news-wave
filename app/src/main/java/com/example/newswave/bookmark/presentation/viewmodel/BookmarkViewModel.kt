package com.example.newswave.bookmark.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newswave.bookmark.domain.use_case.GetBookmarkedNewsUseCase
import com.example.newswave.bookmark.domain.use_case.UnBookmarkNewsUseCase
import com.example.newswave.bookmark.presentation.ui.event.BookmarkEvent
import com.example.newswave.bookmark.presentation.ui.state.BookmarkUiState
import com.example.newswave.core.domain.model.News
import com.example.newswave.core.util.Result
import com.example.newswave.core.presentation.ui.state.NewsItemUiState
import com.example.newswave.core.presentation.ui.utils.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    val getBookmarkedNewsUseCase: GetBookmarkedNewsUseCase,
    val unBookmarkNewsUseCase: UnBookmarkNewsUseCase
):ViewModel() {

    private val _uiState = MutableStateFlow(BookmarkUiState())
    val uiState : StateFlow<BookmarkUiState> =_uiState

    fun onEvent(event:BookmarkEvent){
        when(event){
            BookmarkEvent.LoadBookmark -> loadBookmarkedNews()

            is BookmarkEvent.UnBookmark -> {
                Unit
            }
        }
    }

    private fun loadBookmarkedNews() {
        viewModelScope.launch {
            getBookmarkedNewsUseCase().collect{result ->
                when(result){
                    is Result.Error -> {Log.d("bookmark","${result.error}")}
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                bookmarkedNews = result.data.map {it ->
                                    NewsItemUiState(
                                        it.id,
                                        it.title,
                                        it.author,
                                        it.imageUrl,
                                        it.timestamp,
                                        it.category,
                                        it.link,
                                        true
                                    )
                                }
                            )
                        }
                    }
                }

            }
        }
    }


    fun unBookmark(item:NewsItemUiState){
        viewModelScope.launch {

                unBookmarkNewsUseCase(
                        item.title
                )
                val index=_uiState.value.bookmarkedNews.indexOf(item)
                _uiState.value.bookmarkedNews[index].isBookmarked = false

        }
    }
}