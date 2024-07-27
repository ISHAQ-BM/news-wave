package com.example.newswave.core.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kevinnzou.web.LoadingState
import com.kevinnzou.web.WebView
import com.kevinnzou.web.rememberWebViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailsBottomSheet(
    onDismiss: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(),
    link: String
) {
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = sheetState
    ) {
        Box() {
            val state = rememberWebViewState(url = link)
            val loadingState = state.loadingState
            if (loadingState is LoadingState.Loading) {
                CircularProgressIndicator(
                    progress = { loadingState.progress },
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            LazyColumn(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                userScrollEnabled = true,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                item {
                    WebView(state = state)
                }
            }

        }


    }

}