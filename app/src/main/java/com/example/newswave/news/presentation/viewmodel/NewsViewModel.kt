package com.example.newswave.news.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newswave.core.presentation.ui.theme.util.asUiText
import com.example.newswave.core.util.Result
import com.example.newswave.news.domain.use_case.GetNewsHeadlinesUseCase
import com.example.newswave.news.presentation.ui.event.NewsEvent
import com.example.newswave.news.presentation.ui.state.NewsItemUiState
import com.example.newswave.news.presentation.ui.state.NewsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewsUiState())
    val uiState: StateFlow<NewsUiState> = _uiState


    fun onEvent(event:NewsEvent){
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
                        Log.d("news from vm","${result.error}")
                        it.copy(
                            isLoading = false,
                            generalMessage = result.error.asUiText()
                        )
                    }

                    is Result.Success -> _uiState.update {
                        Log.d("news from vm","${result.data}")
                        it.copy(
                            isLoading = false,
                            articles = result.data.map {it ->
                                NewsItemUiState(
                                    it.id,
                                    it.title,
                                    it.author,
                                    it.imageUrl,
                                    it.timestamp,
                                    it.category?:"",
                                    it.link
                                )
                            }
                        )
                    }

                }
            }
        }
    }



    private val _tasks = getWellnessTasks().toMutableStateList()
    val tasks: List<NewsItemUiState>
        get() = _tasks


    fun remove(item: NewsItemUiState) {
        _tasks.remove(item)
    }
    private fun getWellnessTasks() = List(30) { i -> NewsItemUiState("Task # $i","Task # $i","Task # $i","Task # $i","https://media.gettyimages.com/id/1311148884/vector/abstract-globe-background.jpg?s=612x612&w=0&k=20&c=9rVQfrUGNtR5Q0ygmuQ9jviVUfrnYHUHcfiwaH5-WFE=","Task # $i","Task # $i") }

}


