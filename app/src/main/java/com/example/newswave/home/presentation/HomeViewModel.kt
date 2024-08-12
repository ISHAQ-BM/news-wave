package com.example.newswave.home.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.example.newswave.bookmark.domain.use_case.UnBookmarkNewsUseCase
import com.example.newswave.core.domain.model.News
import com.example.newswave.core.domain.use_case.BookmarkNewsUseCase
import com.example.newswave.core.presentation.ui.state.NewsItemUiState
import com.example.newswave.core.presentation.ui.utils.asUiText
import com.example.newswave.core.util.Result
import com.example.newswave.home.domain.use_case.GetNewsHeadlinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
    val bookmarkNewsUseCase: BookmarkNewsUseCase,
    val unBookmarkNewsUseCase: UnBookmarkNewsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    private val _latestNews = MutableStateFlow<PagingData<NewsItemUiState>>(PagingData.empty())
    val latestNews: StateFlow<PagingData<NewsItemUiState>> = _latestNews


    fun getNewsHeadlines(category: String) {
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            getNewsHeadlinesUseCase(category).collect { result ->
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
                                isLoading = false,
                            )
                        }
                        Log.d("headline news success", category)
                        _latestNews.update {
                            result.data.map {news ->
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

    fun bookmarkClicked(item:NewsItemUiState){
        viewModelScope.launch {
            if (!item.isBookmarked){
                bookmarkNewsUseCase(
                    News(
                        item.id,
                        item.title,
                        item.author,
                        item.category,
                        item.publishDate,
                        item.imageUrl,
                        item.link,
                        true)
                ).collect{result ->
                    when(result){
                        is Result.Error -> {Log.d("bookmark error","${result.error}")}
                        is Result.Success -> {Log.d("bookmark sucess","${result.data}")}
                    }

                }
//                val index=_uiState.value.articles.indexOf(item)
//                _uiState.value.articles[index].isBookmarked = true
                }else{
                unBookmarkNewsUseCase(
                        item.title
                ).collect{result ->
                    when(result){
                        is Result.Error -> {}
                        is Result.Success -> {}

                    }

                }
                //val index=_uiState.value.articles.indexOf(item)
                //_uiState.value.articles[index].isBookmarked = false
            }
        }
    }




}


