package com.example.newswave.search.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.newswave.R
import com.example.newswave.core.presentation.ui.components.PagingNewsList
import com.example.newswave.core.presentation.ui.state.NewsItemUiState
import com.example.newswave.core.presentation.ui.theme.NewsWaveTheme


@Composable
fun SearchRoute(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = hiltViewModel(),
    onClickNews: (String) -> Unit,
    onShareNews: (String) -> Unit,
) {
    val uiState by searchViewModel.uiState.collectAsStateWithLifecycle()
    val searchResult = searchViewModel.searchResult.collectAsLazyPagingItems()

    SearchScreen(
        modifier = modifier,
        uiState = uiState,
        onClickNews = onClickNews,
        searchResult = searchResult,
        onShareNews = onShareNews,
        onSearch = { searchQuery -> searchViewModel.searchNews(searchQuery) },
        onClickBookmark = { item -> searchViewModel.bookmark(item) },
        clearSearchResult = { searchViewModel.clearSearchResult() }

    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    uiState: SearchUiState,
    searchResult: LazyPagingItems<NewsItemUiState>,
    onClickNews: (String) -> Unit,
    onShareNews: (String) -> Unit,
    onSearch: (String) -> Unit,
    onClickBookmark: (NewsItemUiState) -> Unit,
    clearSearchResult: () -> Unit
) {
    var searchQuery by remember {
        mutableStateOf("")
    }
    if (searchQuery.isNullOrEmpty())
        clearSearchResult()

    var isSearching by remember {
        mutableStateOf(false)
    }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    NewsWaveTheme {


        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                @OptIn(ExperimentalMaterial3Api::class)
                SearchBar(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    onSearch = { onSearch(searchQuery) },
                    active = isSearching,
                    onActiveChange = {
                        isSearching = it
                    },
                    placeholder = {
                        Text(text = stringResource(R.string.search_news))
                    },
                    leadingIcon = {
                        if (isSearching)
                            IconButton(onClick = { isSearching = false }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = null
                                )
                            }
                        else
                            Icon(
                                imageVector = Icons.Default.Search,
                                tint = MaterialTheme.colorScheme.onSurface,
                                contentDescription = null
                            )
                    },
                    trailingIcon = {
                        if (!searchQuery.isNullOrEmpty())
                            IconButton(onClick = {
                                searchQuery = ""
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    contentDescription = null
                                )
                            }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = if (isSearching) 0.dp else 16.dp),
                    colors = SearchBarDefaults.colors(
                        containerColor = if (isSearching) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.surfaceContainerHigh,
                    )

                ) {


                    if (uiState.isLoading) {
                        CircularProgressIndicator()
                    } else {
                        PagingNewsList(
                            newsList = searchResult,
                            onClickNews = onClickNews,
                            onClickBookmark = onClickBookmark,
                            onShareNews = onShareNews
                        )
                    }


                }

            }
        ) {

            Column(
                modifier = modifier.padding(top = it.calculateTopPadding())
            ) {

                Spacer(modifier = modifier.height(16.dp))

                Text(
                    text = stringResource(id = R.string.latest_news),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier.padding(horizontal = 24.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
                LatestNewsList(
                    latestNews = uiState.latestNews,
                    onClickNews = onClickNews,
                    onClickBookmark = onClickBookmark,
                    onShareNews = onShareNews
                )

            }


        }

    }

}

@Composable
fun LatestNewsList(
    latestNews: List<NewsItemUiState>,
    onClickNews: (String) -> Unit,
    onClickBookmark: (NewsItemUiState) -> Unit,
    onShareNews: (String) -> Unit,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(
            start = 24.dp,
            end = 24.dp
        )
    ) {
        items(latestNews) { item ->
            LatestNewsItem(
                item = item,
                onClickNews = onClickNews,
                onClickBookmark = onClickBookmark,
                onShareNews = onShareNews
            )

        }
    }

}

@Composable
fun LatestNewsItem(
    modifier: Modifier = Modifier,
    item: NewsItemUiState,
    onClickNews: (String) -> Unit,
    onClickBookmark: (NewsItemUiState) -> Unit,
    onShareNews: (String) -> Unit
) {

    Column(
        modifier = modifier
            .clickable { onClickNews(item.link) }
            .width(250.dp)
    ) {
        AsyncImage(
            model = item.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(250.dp)
                .height(140.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        Text(
            text = item.title,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        Text(
            text = stringResource(R.string.by, item.author ?: "Author"),
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Row(

            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.category,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = modifier.width(4.dp))
            Text(
                text = "•",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = modifier.width(4.dp))

            Text(
                text = item.publishDate,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.weight(1f)
            )
            Box(
                Modifier.height(28.dp)
            ) {
                var expanded by remember { mutableStateOf(false) }
                IconButton(
                    onClick = { expanded = true }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_menu),
                        contentDescription = null
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    properties = PopupProperties(focusable = true)
                ) {

                    DropdownMenuItem(
                        text = { Text(stringResource(id = R.string.share)) },
                        onClick = {
                            onShareNews(item.link)
                            expanded = false
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_share),
                                contentDescription = null
                            )
                        }

                    )
                    DropdownMenuItem(
                        text = { Text(stringResource(id = R.string.bookmark)) },
                        onClick = {
                            onClickBookmark(item)
                            expanded = false
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = if (!item.isBookmarked) R.drawable.ic_bookmark_outlined else R.drawable.ic_bookmark_contained),
                                contentDescription = null
                            )
                        }

                    )

                }
            }
        }

    }


}



