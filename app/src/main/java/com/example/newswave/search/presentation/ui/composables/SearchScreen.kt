package com.example.newswave.search.presentation.ui.composables

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.newswave.core.presentation.ui.components.NewsList
import com.example.newswave.core.presentation.ui.state.NewsItemUiState
import com.example.newswave.search.presentation.ui.event.SearchNewsEvent
import com.example.newswave.search.presentation.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel =  hiltViewModel(),
    onItemClicked:(String)-> Unit,
) {
    val searchUiState by searchViewModel.uiState.collectAsState()
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
        Box(modifier = Modifier.padding(top = it.calculateTopPadding())) {


            NewsList(
                newsList = searchUiState.searchResult,
                onItemClicked = onItemClicked,
                onBookmarkClicked = {item -> searchViewModel.bookmarkClicked(item = item) }
            )
        }
    }
}