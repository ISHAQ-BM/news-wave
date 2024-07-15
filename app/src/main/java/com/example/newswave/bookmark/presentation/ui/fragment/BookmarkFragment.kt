package com.example.newswave.bookmark.presentation.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newswave.R
import com.example.newswave.bookmark.presentation.ui.event.BookmarkEvent
import com.example.newswave.bookmark.presentation.viewmodel.BookmarkViewModel
import com.example.newswave.databinding.FragmentBookmarkedNewsBinding
import com.example.newswave.domain.models.Article
import com.example.newswave.news.presentation.ui.components.NewsList
import com.example.newswave.presentation.viewmodels.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class BookmarkFragment : Fragment() {

    private var binding: FragmentBookmarkedNewsBinding? = null

    private val viewModel: NewsViewModel by activityViewModels()


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
                NewsList(newsListItems = uiState.bookmarkedNews)

            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        binding?.savedRv?.layoutManager= LinearLayoutManager(requireContext())
//        val adapter= NewsAdapter(
//            {article -> displayArticle(article) },
//            object : NewsAdapter.OptionsMenuClickListener{
//                // implement the required method
//                override fun onOptionsMenuClicked(article: Article, view: View) {
//                    // this method will handle the onclick options click
//                    // it is defined below
//                    performOptionsMenuClick(article,view)
//                }
//            }
//        )
//        binding?.savedRv?.adapter=adapter
//        viewModel.getBookmarkedArticles().observe(viewLifecycleOwner) {
//            adapter.submitList(it)
//        }

    }


//    private fun performOptionsMenuClick(article: Article, view: View) {
//        val popUpMenu = PopupMenu(requireContext(), view)
//
//        popUpMenu.inflate(R.menu.popup_menu_baseline_bookmark)
//        popUpMenu.setOnMenuItemClickListener { item ->
//            when (item.itemId) {
//                R.id.share -> {
//                    shareArticle(article.link)
//                    true
//                }
//
//                R.id.bookmark -> {
//                    Log.d("article to delete", "$article")
//                    viewModel.deleteArticle(article)
//                    true
//                }
//
//                else -> {
//                    false
//                }
//            }
//        }
//
//
//
//        popUpMenu.setForceShowIcon(true)
//        popUpMenu.show()
//    }



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