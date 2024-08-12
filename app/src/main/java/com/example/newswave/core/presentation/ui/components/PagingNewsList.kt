package com.example.newswave.core.presentation.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.newswave.core.presentation.ui.state.NewsItemUiState

@Composable
fun PagingNewsList(
    newsList: LazyPagingItems<NewsItemUiState>,
    onClickNews: (String) -> Unit,
    onClickBookmark: (NewsItemUiState) -> Unit,
    onShareNews: (String) -> Unit,
) {
    LazyColumn {
        items(newsList.itemCount) { index ->
            val newsItem = newsList[index]
            newsItem?.let {
                NewsItem(
                    item = it,
                    onClickNews = onClickNews,
                    onClickBookmark = onClickBookmark,
                    onShareNews = onShareNews
                )
                HorizontalDivider()

            }
        }
        item {
            if (newsList.loadState.append is LoadState.Loading) {
                CircularProgressIndicator()
            }
        }
    }

}