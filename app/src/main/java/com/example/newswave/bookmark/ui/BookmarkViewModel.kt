package com.example.newswave.bookmark.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newswave.bookmark.domain.use_case.GetBookmarkedNewsUseCase
import com.example.newswave.bookmark.domain.use_case.UnBookmarkNewsUseCase
import com.example.newswave.core.presentation.ui.state.NewsItemUiState
import com.example.newswave.core.presentation.ui.utils.asUiText
import com.example.newswave.core.util.Result
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

    init {
        getBookmarkNews()
    }


    private fun getBookmarkNews() {
        _uiState.update {
            it.copy(
                isLoading = false
            )
        }
        viewModelScope.launch {
            getBookmarkedNewsUseCase().collect{result ->
                when(result){
                    is Result.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                generalMessage = result.error.asUiText()
                            )
                        }
                    }
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                bookmarkNewsList = result.data.map { it ->
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
        }
    }
}