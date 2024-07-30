package com.example.newswave.interests.presentation.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontVariation.weight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newswave.core.presentation.ui.components.LoadingButton
import com.example.newswave.core.util.categories
import com.example.newswave.interests.presentation.viewmodel.InterestsViewModel


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InterestScreen(
    modifier: Modifier=Modifier,
    viewModel: InterestsViewModel = hiltViewModel(),
    onSaveSuccess :()->Unit

){
    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            (CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
        ),
        title = {
            Text("Interests")
        },
    ))
        }
    ) {
        Column(
            modifier = modifier
                .padding(top = it.calculateTopPadding(), end = 16.dp, start = 16.dp, bottom = 16.dp)
                .fillMaxSize()
        ) {
            val uiState by viewModel.uiState.collectAsState()
            Log.d("interests ui","${uiState.interestsList}")
            onSaveSuccess()


            FlowRow(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f)

            ) {
                categories.forEach { it ->
                    InterestItem(label = it, isSelected =uiState.interestsList.contains(it) ) { interest ->
                        viewModel.interestClicked(interest)
                    }
                }
            }


            Button(
                onClick = { viewModel.updateInterests() },
                modifier=modifier.fillMaxWidth(),
                enabled = uiState.previousInterests.toSet() != uiState.interestsList.toSet()
            ) {
                Text(text = "Save")
            }
        }
    }
}

@Composable
fun InterestItem(
    label:String,
    isSelected:Boolean ,
    onClick : (String)->Unit
){
    var selected by remember(key1 = isSelected) { mutableStateOf(isSelected) }
    FilterChip(
        onClick = {
            selected = !selected
            onClick(label)
                  },
        label = {
            Text(label)
        },
        selected = selected,
    )
}

