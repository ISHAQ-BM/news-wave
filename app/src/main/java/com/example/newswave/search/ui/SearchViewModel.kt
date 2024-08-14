package com.example.newswave.search.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.example.newswave.core.domain.model.News
import com.example.newswave.core.domain.use_case.BookmarkNewsUseCase
import com.example.newswave.core.domain.use_case.UnBookmarkNewsUseCase
import com.example.newswave.core.presentation.ui.state.NewsItemUiState
import com.example.newswave.core.presentation.ui.utils.asUiText
import com.example.newswave.core.util.Result
import com.example.newswave.search.domain.use_case.GetLatestNewsUseCase
import com.example.newswave.search.domain.use_case.SearchNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    val searchNewsUseCase: SearchNewsUseCase,
    val bookmarkNewsUseCase: BookmarkNewsUseCase,
    val unBookmarkNewsUseCase: UnBookmarkNewsUseCase,
    val getLatestNewsUseCase: GetLatestNewsUseCase
):ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState :StateFlow<SearchUiState> = _uiState

    private val _searchResult = MutableStateFlow<PagingData<NewsItemUiState>>(PagingData.empty())
    val searchResult: StateFlow<PagingData<NewsItemUiState>> = _searchResult

    init {
        getLatestNews()
    }

    fun clearSearchResult() {
        _searchResult.update {
            PagingData.empty()
        }
    }


    fun searchNews(searchQuery: String) {
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            searchNewsUseCase(searchQuery).collect{ result ->
                when (result) {
                    is Result.Error -> _uiState.update {
                        it.copy(
                            isLoading = false,
                            generalMessage = result.error.asUiText()
                        )
                    }

                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                        _searchResult.update {
                            result.data.map { news ->
                                NewsItemUiState(
                                    id = news.id,
                                    title = news.title,
                                    author = news.author,
                                    imageUrl = news.imageUrl,
                                    publishDate = news.timestamp,
                                    category = news.category,
                                    link = news.link,
                                    isBookmarked = false
                                )
                            }
                        }
                    }
                }

            }
        }
    }

    fun bookmark(item: NewsItemUiState) {
        viewModelScope.launch {
            if (!item.isBookmarked) {
                bookmarkNewsUseCase(
                    News(
                        item.id,
                        item.title,
                        item.author,
                        item.category,
                        item.publishDate,
                        item.imageUrl,
                        item.link,
                        true
                    )
                ).collect { result ->
                    when (result) {
                        is Result.Error -> _uiState.update {
                            it.copy(
                                generalMessage = result.error.asUiText()
                            )
                        }

                        is Result.Success -> {}
                    }

                }

            } else {

                unBookmarkNewsUseCase(
                    item.title
                ).collect { result ->
                    when (result) {
                        is Result.Error -> _uiState.update {
                            it.copy(
                                generalMessage = result.error.asUiText(),
                            )
                        }

                        is Result.Success -> {}
                    }

                }

            }
        }
    }

    private fun getLatestNews(){
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            getLatestNewsUseCase().collect{result ->
                when(result){
                    is Result.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                generalMessage = result.error.asUiText()
                            )
                        }
                    }
                    is Result.Success -> _uiState.update {
                        it.copy(
                            isLoading = false,
                            latestNews = result.data.map { news ->
                                NewsItemUiState(
                                    id = news.id,
                                    title = news.title,
                                    author = news.author,
                                    imageUrl = news.imageUrl,
                                    publishDate = news.timestamp,
                                    category = news.category,
                                    link = news.link,
                                    isBookmarked = false
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}