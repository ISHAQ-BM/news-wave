package com.example.newswave.news_details.presentation.ui.composables

import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.viewinterop.AndroidView
import com.example.newswave.R


@Composable
fun NewsDetailsScreen(
    link: String
) {
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
                        IconButton(onClick = { }) {
                            Icon(
                                painter = painterResource(
                                    id = R.drawable.ic_outline_bookmark
                                ),
                                contentDescription = ""
                            )
                        }
                        IconButton(onClick = { }) {
                            Icon(
                                painter = painterResource(
                                    id = R.drawable.ic_share
                                ),
                                contentDescription = ""
                            )
                        }
                    }
                }
            )
        },

        ) {
        Column(
            modifier = Modifier.padding(top = it.calculateTopPadding())
        ) {
            WebView(mUrl = link)
        }

    })
}


@Composable
fun WebView(
    mUrl: String,
) {

    AndroidView(factory = {
        android.webkit.WebView(it).apply {
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