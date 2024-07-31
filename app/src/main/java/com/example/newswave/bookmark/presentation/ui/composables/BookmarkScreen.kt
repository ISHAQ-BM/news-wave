package com.example.newswave.bookmark.presentation.ui.composables

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.newswave.bookmark.presentation.ui.event.BookmarkEvent
import com.example.newswave.bookmark.presentation.viewmodel.BookmarkViewModel
import com.example.newswave.core.presentation.ui.components.NewsList

@Composable
fun BookmarkScreen(
    onItemClicked:(String)-> Unit,
    bookmarkViewModel: BookmarkViewModel =  hiltViewModel(),
    onShareNews :(String)-> Unit,
) {
    val uiState by bookmarkViewModel.uiState.collectAsState()
    bookmarkViewModel.onEvent(BookmarkEvent.LoadBookmark)
    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            (TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
        ),
        title = {
            Text("Bookmark")
        },
    ))
        }
    ) {
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxWidth()
        ) {
            NewsList(
                newsList = uiState.bookmarkedNews,
                onItemClicked = onItemClicked,
                onBookmarkClicked = {item -> bookmarkViewModel.unBookmark(item = item) },
                onShareNews = onShareNews
            )

        }
    }

}