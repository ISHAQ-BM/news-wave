package com.example.newswave.news_details.presentation.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newswave.R
import com.example.newswave.core.presentation.ui.theme.NewsWaveTheme
import com.example.newswave.databinding.FragmentNewsDetailsBinding

import com.example.newswave.presentation.viewmodels.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailsFragment : Fragment() {

    private var binding: FragmentNewsDetailsBinding? = null
    private val args: NewsDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsDetailsBinding.inflate(inflater, container, false).apply {
            composeView.setContent {
                NewsWaveTheme {
                    @OptIn(ExperimentalMaterial3Api::class)
                    (Scaffold(
                        topBar = {
                            TopAppBar(
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    titleContentColor = MaterialTheme.colorScheme.primary,
                                ),
                                title = {},
                                actions = {
                                    Row {
                                        IconButton(onClick = {  }) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_outline_bookmark),
                                                contentDescription = ""
                                            )
                                        }
                                        IconButton(onClick = {  }) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_share),
                                                contentDescription = ""
                                            )
                                        }
                                    }
                                }
                            )
                        },

                        ) {
                        Column (
                            modifier = Modifier.padding(top = it.calculateTopPadding())
                        ){
                            WebView(mUrl = args.link)
                        }

                    })
                }
            }


        }
        return binding?.root
    }

    @Composable
    fun WebView(
        mUrl: String,
    ) {

        AndroidView(factory = {
            WebView(it).apply {
                this.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                this.webViewClient = WebViewClient()
            }
        }, update = {
            it.loadUrl(mUrl)
        })
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