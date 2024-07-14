package com.example.newswave.home.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newswave.core.presentation.ui.theme.NewsWaveTheme
import com.example.newswave.databinding.FragmentHomeBinding
import com.example.newswave.core.util.categories
import com.example.newswave.news.presentation.ui.components.NewsList
import com.example.newswave.news.presentation.ui.event.NewsEvent
import com.example.newswave.news.presentation.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            composeView.setContent {
                HomeScreen()
            }
        }
        return binding?.root
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun HomeScreen(
        newsViewsModel: NewsViewModel = viewModel()
    ){
        NewsWaveTheme {

            val uiState by newsViewsModel.uiState.collectAsState()
            newsViewsModel.onEvent(NewsEvent.CategoryChanged("top"))
            Scaffold (
                topBar = {
                    @OptIn(ExperimentalMaterial3Api::class)
                    CenterAlignedTopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            titleContentColor = MaterialTheme.colorScheme.onBackground,
                        ),
                        title = {
                            Text("NewsWave")
                        },
                    )
                }
            ){
                Column (
                    modifier  = Modifier
                        .padding(top = it.calculateTopPadding())
                        .fillMaxWidth()
                ){

                    val pagerState = rememberPagerState(pageCount = { categories.size   })
                    //val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
                    val selectedTabIndex = remember { mutableStateOf(pagerState.currentPage ) }
                    ScrollableTabRow(
                        containerColor = Color.Transparent,
                        selectedTabIndex = selectedTabIndex.value,
                        modifier = Modifier.fillMaxWidth(),
                        edgePadding = 0.dp
                    ){
                        categories.forEachIndexed{ index ,currentTab ->
                            Tab(
                                selected = selectedTabIndex.value == index,
                                onClick = {
                                    selectedTabIndex.value = index
                                    newsViewsModel.onEvent(NewsEvent.CategoryChanged(categories[selectedTabIndex.value]))
                                   },
                                text = { Text(text = currentTab)}
                            )

                        }
                    }
                    @OptIn(ExperimentalFoundationApi::class)
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            NewsList(newsListItems = uiState.articles)

                        }
                    }
                }
            }
        }

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null

    }


}