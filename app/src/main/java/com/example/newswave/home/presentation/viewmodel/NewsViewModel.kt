package com.example.newswave.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newswave.core.presentation.ui.utils.asUiText
import com.example.newswave.core.util.Result
import com.example.newswave.home.domain.use_case.GetNewsHeadlinesUseCase
import com.example.newswave.home.presentation.ui.event.NewsEvent
import com.example.newswave.core.presentation.ui.state.NewsItemUiState
import com.example.newswave.core.presentation.ui.state.NewsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewsUiState())
    val uiState: StateFlow<NewsUiState> = _uiState


    fun onEvent(event: NewsEvent){
        when(event){
            is NewsEvent.ArticleSelected -> TODO()
            is NewsEvent.BookmarkArticle -> TODO()
            NewsEvent.LoadBookmarkedArticles -> TODO()
            is NewsEvent.UnBookmarkArticle -> TODO()
            is NewsEvent.CategoryChanged -> {
                _uiState.update {
                    it.copy(category = event.category)

                }
                getNewsHeadlines()
            }
        }
    }

    fun getNewsHeadlines() {
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            getNewsHeadlinesUseCase(_uiState.value.category).collect{result ->
                when(result){
                    is Result.Error -> _uiState.update {
                        it.copy(
                            isLoading = false,
                            generalMessage = result.error.asUiText()
                        )
                    }

                    is Result.Success -> _uiState.update {
                        it.copy(
                            isLoading = false,
                            articles = result.data.map {it ->
                                NewsItemUiState(
                                    it.id,
                                    it.title,
                                    it.author,
                                    it.imageUrl,
                                    it.timestamp,
                                    it.category,
                                    it.link,
                                    false
                                )
                            }
                        )
                    }

                }
            }
        }
    }




}


