package com.example.newswave.home.presentation.ui.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.newswave.core.presentation.ui.components.NewsItem
import com.example.newswave.core.presentation.ui.state.NewsItemUiState
import com.example.newswave.core.presentation.ui.theme.NewsWaveTheme
import com.example.newswave.core.util.categories
import com.example.newswave.home.presentation.ui.event.HomeEvent
import com.example.newswave.home.presentation.viewmodel.HomeViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel =  hiltViewModel(),
    onItemClicked:(String)-> Unit,
    onShareNews :(String)-> Unit,
    listState: LazyListState = rememberLazyListState()
){
    NewsWaveTheme {
        val articles: LazyPagingItems<NewsItemUiState> = homeViewModel.articles.collectAsLazyPagingItems()

        homeViewModel.onEvent(HomeEvent.CategoryChanged("top"))
        Scaffold (
            topBar = {
                @OptIn(ExperimentalMaterial3Api::class)
                (CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
        ),
        title = {
            Text("NewsWave")
        },
    ))
            }
        ){
            Column (
                modifier  = Modifier
                    .padding(top = it.calculateTopPadding())
                    .fillMaxWidth()
            ){

                val pagerState = rememberPagerState(pageCount = { categories.size   })
                //val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
                val selectedTabIndex = remember { mutableStateOf(pagerState.currentPage ) }
                ScrollableTabRow(
                    containerColor = Color.Transparent,
                    selectedTabIndex = selectedTabIndex.value,
                    modifier = Modifier.fillMaxWidth(),
                    edgePadding = 0.dp
                ){
                    categories.forEachIndexed{ index, currentTab ->
                        Tab(
                            selected = selectedTabIndex.value == index,
                            onClick = {
                                selectedTabIndex.value = index
                                homeViewModel.onEvent(HomeEvent.CategoryChanged(categories[selectedTabIndex.value]))
                            },
                            text = { Text(text = currentTab) }
                        )

                    }
                }
                @OptIn(ExperimentalFoundationApi::class)
                (HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            HomeNewsList(
                news = articles,
                onItemClicked = onItemClicked,
                listState = listState,
                onBookmarkClicked = {item -> homeViewModel.bookmarkClicked(item = item) },
                onShareNews = onShareNews
            )

        }
    })
            }
        }
    }

}

@Composable
fun HomeNewsList(
    news: LazyPagingItems<NewsItemUiState>,
    onItemClicked: (String)->Unit,
    onBookmarkClicked :(NewsItemUiState)->Unit,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    onShareNews :(String)-> Unit,
) {

    LazyColumn(
        state = listState,
        modifier = modifier,
    ) {
        items(news.itemCount) { index ->
            val newsItem = news[index]
            newsItem?.let {
                NewsItem(
                    item = it,
                    onItemClicked = onItemClicked,
                    onBookmarkClicked = onBookmarkClicked,
                    onShareNews = onShareNews
                )
                HorizontalDivider()

            }
        }
        item {
            if(news.loadState.append is LoadState.Loading) {
                CircularProgressIndicator()
            }
        }
    }





}
