package com.example.newswave.home.presentation.ui.composables

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.navigation.NavHostController
import com.example.newswave.core.presentation.ui.components.NewsList
import com.example.newswave.core.presentation.ui.theme.NewsWaveTheme
import com.example.newswave.core.util.categories
import com.example.newswave.home.presentation.ui.event.NewsEvent
import com.example.newswave.home.presentation.viewmodel.NewsViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    newsViewsModel: NewsViewModel =  hiltViewModel(),
    onItemClicked:(String)-> Unit,
    listState: LazyListState = rememberLazyListState()
){
    NewsWaveTheme {

        val uiState by newsViewsModel.uiState.collectAsState()
        newsViewsModel.onEvent(NewsEvent.CategoryChanged("top"))
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
                                newsViewsModel.onEvent(NewsEvent.CategoryChanged(categories[selectedTabIndex.value]))
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
            NewsList(
                newsList = uiState.articles,
                onItemClicked = onItemClicked,
                listState = listState
            )

        }
    })
            }
        }
    }

}