package com.example.newswave.news.presentation.ui.fragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newswave.databinding.FragmentNewsBinding
import com.example.newswave.news.presentation.ui.components.NewsList
import com.example.newswave.news.presentation.ui.event.NewsEvent
import com.example.newswave.news.presentation.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private var binding: FragmentNewsBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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


    @Composable
    fun News(
        newsViewsModel :NewsViewModel = viewModel()
    ){
        val uiState by newsViewsModel.uiState.collectAsState()
        newsViewsModel.onEvent(NewsEvent.CategoryChanged(arguments?.getString("category") ?: "top"))
        NewsList(newsListItems = uiState.articles)

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