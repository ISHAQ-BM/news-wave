package com.example.newswave.search.presentation.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newswave.core.presentation.ui.theme.NewsWaveTheme
import com.example.newswave.databinding.FragmentSearchNewsBinding
import com.example.newswave.news.presentation.ui.components.NewsList
import com.example.newswave.news.presentation.ui.state.NewsItemUiState
import com.example.newswave.search.presentation.ui.event.SearchNewsEvent
import com.example.newswave.search.presentation.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchNewsFragment : Fragment() {

    private var binding: FragmentSearchNewsBinding? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchNewsBinding.inflate(inflater, container, false).apply {
            composeView.setContent {
                NewsWaveTheme {
                    SearchScreen()
                }

            }
        }
        return binding?.root
    }

    @Composable
    fun SearchScreen(
        searchViewModel: SearchViewModel = viewModel()
    ){
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
                SearchBar(
                    query = searchQuery,
                    onQueryChange = {searchQuery = it},
                    onSearch = {searchViewModel.onEvent(SearchNewsEvent.ToggleSearch(searchQuery))}, //the callback to be invoked when the input service triggers the ImeAction.Search action
                    active = isSearching, //whether the user is searching or not
                    onActiveChange = {isSearching=!it }, //the callback to be invoked when this search bar's active state is changed
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

                }
            },
        ){innerPadding ->
            News(newsListItems = searchUiState.searchResult,innerPadding)
        }
    }


    @Composable
    fun News(
        newsListItems:List<NewsItemUiState>,
        innerPaddingValues: PaddingValues,
        modifier: Modifier = Modifier
    ){
        Box(modifier = modifier.padding(innerPaddingValues)){
            NewsList(newsListItems =newsListItems )
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