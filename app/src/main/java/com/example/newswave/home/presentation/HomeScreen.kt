package com.example.newswave.home.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newswave.R
import com.example.newswave.core.presentation.ui.components.PagingNewsList
import com.example.newswave.core.presentation.ui.state.NewsItemUiState
import com.example.newswave.core.util.categories
import kotlinx.coroutines.launch


@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onClickNews: (String) -> Unit,
    onShareNews: (String) -> Unit,
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val latestNews = homeViewModel.latestNews.collectAsLazyPagingItems()
    HomeScreen(
        modifier = modifier,
        uiState = uiState,
        onClickNews = onClickNews,
        onShareNews = onShareNews,
        latestNews = latestNews,
        onClickBookmark = { item -> homeViewModel.bookmark(item) },
        getNewsByCategory = { category -> homeViewModel.getNewsHeadlines(category) }
    )

}


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    latestNews: LazyPagingItems<NewsItemUiState>,
    onClickNews: (String) -> Unit,
    onShareNews: (String) -> Unit,
    onClickBookmark: (NewsItemUiState) -> Unit,
    getNewsByCategory: (String) -> Unit
) {

    getNewsByCategory("top")
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                ),
                title = {
                    Icon(
                        painter = painterResource(id = R.drawable.logo_icon),
                        contentDescription = null
                    )
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_notification),
                            contentDescription = null
                        )
                    }
                },
                scrollBehavior = scrollBehavior

            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxWidth()
        ) {

            val scope = rememberCoroutineScope()
            val pagerState = rememberPagerState(pageCount = { categories.size })
            val selectedTabIndex by remember { derivedStateOf { pagerState.currentPage } }
            ScrollableTabRow(
                containerColor = Color.Transparent,
                selectedTabIndex = selectedTabIndex,
                modifier = modifier.fillMaxWidth(),
                edgePadding = 0.dp
            ) {
                categories.forEachIndexed { index, currentTab ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = {
                            scope.launch {
                                pagerState.scrollToPage(index)
                            }
                            getNewsByCategory(currentTab)
                        },
                        text = { Text(text = currentTab) }
                    )

                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = modifier.fillMaxSize()
            ) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (uiState.isLoading)
                        CircularProgressIndicator()
                    PagingNewsList(
                        newsList = latestNews,
                        onClickNews = onClickNews,
                        onClickBookmark = onClickBookmark,
                        onShareNews = onShareNews
                    )

                }
            }
        }
    }
}




