package com.example.newswave.search.presentation.ui.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newswave.R
import com.example.newswave.databinding.FragmentSearchNewsBinding
import com.example.newswave.domain.models.Article
import com.example.newswave.core.Resource
import com.example.newswave.core.presentation.ui.theme.NewsWaveTheme
import com.example.newswave.news.presentation.adapter.NewsAdapter
import com.example.newswave.news.presentation.ui.components.NewsList
import com.example.newswave.news.presentation.ui.fragment.NewsFragment
import com.example.newswave.news.presentation.ui.state.NewsItemUiState
import com.example.newswave.presentation.viewmodels.NewsViewModel
import com.example.newswave.search.presentation.ui.event.SearchNewsEvent
import com.example.newswave.search.presentation.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchNewsFragment : Fragment() {

    private var binding: FragmentSearchNewsBinding? = null

    private val viewModel: NewsViewModel by activityViewModels()



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



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
//        val adapter= NewsAdapter(
//            {article -> displayArticle(article) },
//            object : NewsAdapter.OptionsMenuClickListener{
//                // implement the required method
//                @RequiresApi(Build.VERSION_CODES.Q)
//                override fun onOptionsMenuClicked(article: Article, view: View) {
//                    // this method will handle the onclick options click
//                    // it is defined below
//                    performOptionsMenuClick(article,view)
//                }
//            }
//        )
//
//        binding?.recyclerView?.adapter = adapter
//
//        binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                binding?.searchView!!.clearFocus()
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                viewModel.searchNews(newText)
//                return true
//            }
//        })
//
//        viewModel.searchNews.observe(viewLifecycleOwner) {
//            when(it){
//                is Resource.Loading ->{
//                    binding?.statusImage?.visibility = View.GONE
//                    binding?.progressbar?.visibility=View.VISIBLE
//                }
//                is Resource.Success ->{
//                    binding?.statusImage?.visibility = View.GONE
//                    binding?.progressbar?.visibility=View.INVISIBLE
//                    adapter.submitList(it.data?.articles)
//                }
//                is Resource.Error ->{
//                    binding?.progressbar?.visibility=View.INVISIBLE
//                    if (it.message == "No internet connection")
//                        binding?.statusImage?.visibility = View.VISIBLE
//
//
//                }
//            }
//        }
//
   }

//
//    @RequiresApi(Build.VERSION_CODES.Q)
//    private fun performOptionsMenuClick(article: Article, view: View) {
//        val popupMenu = PopupMenu(requireContext() , view)
//        if (article.isBookmarked) {
//            popupMenu.inflate(R.menu.popup_menu_baseline_bookmark)
//            popupMenu.setOnMenuItemClickListener { item ->
//                when (item.itemId) {
//                    R.id.share -> {
//                        shareArticle(article.link)
//                        true
//                    }
//
//                    R.id.bookmark -> {
//                        viewModel.deleteArticle(article)
//                        Toast.makeText(requireContext(), "article unsaved", Toast.LENGTH_LONG)
//                            .show()
//                        true
//                    }
//
//                    else -> {
//                        false
//                    }
//                }
//            }
//        } else {
//            popupMenu.inflate(R.menu.popup_menu_outline_bookmark)
//            popupMenu.setOnMenuItemClickListener { item ->
//                when (item.itemId) {
//                    R.id.share -> {
//                        shareArticle(article.link)
//                        true
//                    }
//
//                    R.id.bookmark -> {
//                        viewModel.bookmarkArticle(article)
//                        Toast.makeText(requireContext(), "article saved", Toast.LENGTH_LONG).show()
//                        true
//                    }
//
//                    else -> {
//                        false
//                    }
//                }
//
//            }
//        }
//        popupMenu.setForceShowIcon(true)
//        popupMenu.show()
//
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

    private fun displayArticle(article: Article) {
//        val action = SearchNewsFragmentDirections.actionSearchNewsFragmentToArticleFragment(article)
//        findNavController().navigate(action)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null

    }

    @Preview(showBackground = true)
    @Composable
    fun searchScreenPreview(){
        NewsWaveTheme {
            SearchScreen()
        }
    }


}