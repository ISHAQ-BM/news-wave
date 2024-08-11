package com.example.newswave.bookmark.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newswave.R
import com.example.newswave.core.presentation.ui.components.NewsList
import com.example.newswave.core.presentation.ui.state.NewsItemUiState

@Composable
fun BookmarkRoute(
    modifier: Modifier = Modifier,
    bookmarkViewModel: BookmarkViewModel = hiltViewModel(),
    onClickNews: (String) -> Unit,
    onShareNews: (String) -> Unit,
    navigateToHome: () -> Unit
) {
    val uiState by bookmarkViewModel.uiState.collectAsStateWithLifecycle()
    BookmarkScreen(
        modifier = modifier,
        uiState = uiState,
        onClickNews = onClickNews,
        onShareNews = onShareNews,
        onClickBookmark = { title -> bookmarkViewModel.unBookmark(title) },
        navigateToHome
    )

}


@Composable
fun BookmarkScreen(
    modifier: Modifier = Modifier,
    uiState: BookmarkUiState,
    onClickNews: (String) -> Unit,
    onShareNews: (String) -> Unit,
    onClickBookmark: (NewsItemUiState) -> Unit,
    navigateToHome: () -> Unit
) {
    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            (TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                ),
                title = {
                    Text(
                        text = stringResource(id = R.string.bookmark),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold

                    )
                },
            ))
        }
    ) {
        Box(
            modifier = modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxSize(),
            contentAlignment = Alignment.Center

        ) {
            if (uiState.isLoading)
                CircularProgressIndicator()
            else if (uiState.bookmarkNewsList.isEmpty() && (uiState.generalMessage == null)) {
                EmptyBookmarksContent(
                    modifier = modifier,
                    navigateToHome = navigateToHome
                )
            }

            NewsList(
                newsList = uiState.bookmarkNewsList,
                onItemClicked = onClickNews,
                onBookmarkClicked = onClickBookmark,
                onShareNews = onShareNews
            )


        }
    }

}


@Composable
fun EmptyBookmarksContent(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.your_bookmarks_are_empty),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = modifier.height(24.dp))
        Text(
            text = stringResource(R.string.you_haven_t_saved),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = modifier.height(72.dp))
        Button(onClick = { navigateToHome() }) {
            Text(text = stringResource(R.string.explore_latest_news))
        }
    }
}