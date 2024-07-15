package com.example.newswave.news.presentation.ui.fragment

import android.content.Intent
import android.os.Build

import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.compose.AsyncImage
import com.example.newswave.R
import com.example.newswave.domain.models.Article
import com.example.newswave.core.presentation.ui.theme.NewsWaveTheme
import com.example.newswave.databinding.FragmentNewsBinding
import com.example.newswave.home.presentation.ui.fragment.HomeFragmentDirections
import com.example.newswave.news.presentation.ui.components.NewsList
import com.example.newswave.news.presentation.ui.components.NewsListItem
import com.example.newswave.news.presentation.ui.event.NewsEvent
import com.example.newswave.news.presentation.ui.state.NewsItemUiState
//import com.example.newswave.presentation.viewmodels.NewsViewModel
import com.example.newswave.news.presentation.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private var binding: FragmentNewsBinding? = null


    //private val viewModel: NewsViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        binding= FragmentNewsBinding.inflate(inflater, container, false)
//        return binding?.root
        val binding = FragmentNewsBinding.inflate(
            inflater, container, false
        ).apply {
            composeView.setContent {
                MaterialTheme {
                    News()
                }
            }
        }

        return binding?.root
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

//        binding?.recyclerView?.adapter=adapter
//        binding?.recyclerView?.addOnScrollListener(scrollListener)

//        viewModel.latestNews.observe(viewLifecycleOwner) {
//            when(it){
//                is Resource.Loading -> {
//                    adapter.submitList(it.data?.articles)
//                    binding?.statusImage?.visibility =View.INVISIBLE
//                    binding?.progressbar?.visibility = View.VISIBLE
//
//                }
//                is Resource.Success ->  {
//                    binding?.statusImage?.visibility =View.INVISIBLE
//                    binding?.progressbar?.visibility=View.INVISIBLE
//                    adapter.submitList(it.data?.articles)
//                }
//                is Resource.Error -> {
//                    adapter.submitList(it.data?.articles)
//                    binding?.progressbar?.visibility = View.INVISIBLE
//                    binding?.statusImage?.visibility =View.VISIBLE
//                }
//            }
//        }

    }

    @Composable
    fun News(
        newsViewsModel :NewsViewModel = viewModel()
    ){
        val uiState by newsViewsModel.uiState.collectAsState()
        newsViewsModel.onEvent(NewsEvent.CategoryChanged(arguments?.getString("category") ?: "top"))
        NewsList(newsListItems = uiState.articles)

    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun performOptionsMenuClick(article: Article, view: View) {
        val popupMenu = PopupMenu(requireContext() , view)
        if (article.isBookmarked) {
            popupMenu.inflate(R.menu.popup_menu_baseline_bookmark)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.share -> {
                        shareArticle(article.link)
                        true
                    }

                    R.id.bookmark -> {
                        //viewModel.deleteArticle(article)
                        Toast.makeText(requireContext(), "article unsaved", Toast.LENGTH_LONG)
                            .show()
                        true
                    }

                    else -> {
                        false
                    }
                }
            }
        } else {
            popupMenu.inflate(R.menu.popup_menu_outline_bookmark)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.share -> {
                        shareArticle(article.link)
                        true
                    }

                    R.id.bookmark -> {
                        //viewModel.bookmarkArticle(article)
                        Toast.makeText(requireContext(), "article saved", Toast.LENGTH_LONG).show()
                        true
                    }

                    else -> {
                        false
                    }
                }

            }
        }
        popupMenu.setForceShowIcon(true)
        popupMenu.show()

    }

    private fun displayArticle(article: Article) {
//        val action=HomeFragmentDirections.actionHomeFragmentToArticleFragment(article)
//        findNavController().navigate(action)
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




    @Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
    @Composable
    fun NewsItemPreview() {
        NewsWaveTheme {
            NewsListItem(newsItem = NewsItemUiState("qw","eszrhrh","","ishaq bm","https://images.unsplash.com/photo-1613323593608-abc90fec84ff?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8OHx8aW1hZ2V8ZW58MHx8MHx8fDA%3D","5h ago","Top",false) , navigateToDetail = {})
        }
    }
    @Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
    @Composable
    fun NewsListPreview() {
        NewsWaveTheme {
            NewsList(newsListItems = listOf(NewsItemUiState("qw","eszrhrh","","ishaq bm","https://images.unsplash.com/photo-1613323593608-abc90fec84ff?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8OHx8aW1hZ2V8ZW58MHx8MHx8fDA%3D","5h ago","Top",false) ))

        }
    }



}