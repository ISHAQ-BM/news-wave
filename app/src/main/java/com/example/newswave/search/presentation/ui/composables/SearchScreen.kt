package com.example.newswave.search.presentation.ui.composables

import android.widget.Space
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.newswave.R
import com.example.newswave.core.presentation.ui.components.NewsItem
import com.example.newswave.core.presentation.ui.state.NewsItemUiState
import com.example.newswave.core.util.youMightLikeSuggestions
import com.example.newswave.search.presentation.ui.event.SearchNewsEvent
import com.example.newswave.search.presentation.viewmodel.SearchViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel(),
    onItemClicked: (String) -> Unit,
    onShareNews: (String) -> Unit,
) {
    val searchUiState by searchViewModel.uiState.collectAsState()
    val articles: LazyPagingItems<NewsItemUiState> =
        searchViewModel.articles.collectAsLazyPagingItems()

    var searchQuery by remember {
        mutableStateOf("")
    }

    var isSearching by remember {
        mutableStateOf(false)
    }
    Scaffold(

        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            (SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = { searchViewModel.onEvent(SearchNewsEvent.ToggleSearch(searchQuery)) }, //the callback to be invoked when the input service triggers the ImeAction.Search action
                active = isSearching,
                onActiveChange = {
                    isSearching = !it
                },
                placeholder = {
                    Text(text = "Search news")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = null
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

            })
        },
    ) {
        Box(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
        ) {
            if (searchQuery.isNullOrEmpty()){
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = stringResource(id = R.string.you_might_like),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)

                    ) {
                        youMightLikeSuggestions.forEach { it ->
                            ElevatedSuggestionChip(
                                onClick = { },
                                label = { Text(text = it) },
                                colors = SuggestionChipDefaults.suggestionChipColors(
                                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                    labelColor = MaterialTheme.colorScheme.onTertiaryContainer
                                )
                            )

                        }
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.latest_news),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(
                            start = 24.dp,
                            end = 24.dp
                        )
                    ) {
                        items(searchUiState.latestNews) { item ->
                            VerticalNewsItem(
                                item = item,
                                onItemClicked = onItemClicked,
                                onBookmarkClicked = {},
                                onShareNews = onShareNews
                            )

                        }
                    }

                }
            }else{
                SearchNewsList(
                    news = articles,
                    onItemClicked = onItemClicked,
                    onBookmarkClicked = { item -> searchViewModel.bookmarkClicked(item = item) },
                    onShareNews = onShareNews
                )
            }



        }



    }
}

@Composable
fun SearchNewsList(
    news: LazyPagingItems<NewsItemUiState>,
    onItemClicked: (String) -> Unit,
    onBookmarkClicked: (NewsItemUiState) -> Unit,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    onShareNews: (String) -> Unit,
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
            if (news.loadState.append is LoadState.Loading) {
                CircularProgressIndicator()
            }
        }
    }


}


@Composable
fun VerticalNewsItem(
    modifier: Modifier = Modifier,
    item: NewsItemUiState,
    onItemClicked: (String) -> Unit,
    onBookmarkClicked: (NewsItemUiState) -> Unit,
    onShareNews: (String) -> Unit
) {

    Column(
        modifier = modifier
            .clickable { onItemClicked(item.link) }
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
            text = "By ${item.author}",
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
                        contentDescription = "More options"
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    properties = PopupProperties(focusable = true)
                ) {

                    DropdownMenuItem(
                        text = { Text("Share") },
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
                        text = { Text("Bookmark") },
                        onClick = {
                            onBookmarkClicked(item)
                            expanded = false
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = if (!item.isBookmarked) R.drawable.ic_outline_bookmark else R.drawable.ic_baseline_bookmark),
                                contentDescription = null
                            )
                        }

                    )

                }
            }
        }


    }


}

