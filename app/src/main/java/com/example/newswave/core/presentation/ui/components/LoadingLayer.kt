package com.example.newswave.core.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.tooling.preview.Preview
import com.example.newswave.core.presentation.ui.theme.NewsWaveTheme

@Composable
fun LoadingLayer(){
    Box (

        Modifier
            .fillMaxSize()
            .background(Color(0x3D000000)),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator()
    }
}

