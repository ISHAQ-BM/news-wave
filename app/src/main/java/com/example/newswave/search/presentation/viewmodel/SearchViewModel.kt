package com.example.newswave.search.presentation.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newswave.core.presentation.ui.utils.asUiText
import com.example.newswave.core.util.Result
import com.example.newswave.news.presentation.ui.state.NewsItemUiState
import com.example.newswave.search.domain.use_case.SearchNewsUseCase
import com.example.newswave.search.presentation.ui.event.SearchNewsEvent
import com.example.newswave.search.presentation.ui.state.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    val searchNewsUseCase: SearchNewsUseCase
):ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState :StateFlow<SearchUiState> = _uiState

    fun onEvent(event:SearchNewsEvent){
        when(event){
            is SearchNewsEvent.ToggleSearch -> searchNews(event.searchQuery)
        }

    }

    private fun searchNews(searchQuery: String) {
        viewModelScope.launch {
            searchNewsUseCase(searchQuery).collect{result ->
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
                            searchResult = result.data.map {it ->
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