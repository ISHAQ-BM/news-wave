package com.example.newswave.bookmark.presentation.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newswave.bookmark.presentation.ui.event.BookmarkEvent
import com.example.newswave.bookmark.presentation.viewmodel.BookmarkViewModel
import com.example.newswave.databinding.FragmentBookmarkedNewsBinding
import com.example.newswave.news.presentation.ui.components.NewsList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class BookmarkFragment : Fragment() {

    private var binding: FragmentBookmarkedNewsBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBookmarkedNewsBinding.inflate(inflater, container, false).apply {
            composeView.setContent {
                BookmarkedNewsScreen()
            }
        }
        return binding?.root
    }

    @Composable
    fun BookmarkedNewsScreen(
        bookmarkViewModel: BookmarkViewModel = viewModel()
    ) {
        val uiState by bookmarkViewModel.uiState.collectAsState()
        bookmarkViewModel.onEvent(BookmarkEvent.LoadBookmark)
        Scaffold(
            topBar = {
                @OptIn(ExperimentalMaterial3Api::class)
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        titleContentColor = MaterialTheme.colorScheme.onBackground,
                    ),
                    title = {
                        Text("Bookmark")
                    },
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(top = it.calculateTopPadding())
                    .fillMaxWidth()
            ) {
                NewsList(
                    newsListItems = uiState.bookmarkedNews,
                    navigationToDetails = {}
                )

            }
        }

    }



    private fun shareArticle(link: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, link)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null

    }


}