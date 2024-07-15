package com.example.newswave.news.presentation.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.fragment.findNavController
import com.example.newswave.home.presentation.ui.fragment.HomeFragmentDirections
import com.example.newswave.news.presentation.ui.state.NewsItemUiState

@Composable
fun NewsList(
    modifier: Modifier = Modifier,
    newsListItems : List<NewsItemUiState>,
    navigationToDetails:(String)->Unit
){
    LazyColumn (
        modifier = modifier,
    ){
        items(items = newsListItems, key = { item -> item.id }) { item ->
            NewsListItem(newsItem = item, navigateToDetail = {link ->
                navigationToDetails(link)
            })
            HorizontalDivider()
        }




    }
}