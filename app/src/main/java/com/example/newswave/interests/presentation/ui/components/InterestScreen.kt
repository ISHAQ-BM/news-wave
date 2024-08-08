package com.example.newswave.interests.presentation.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontVariation.weight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newswave.R
import com.example.newswave.core.presentation.ui.components.LoadingButton
import com.example.newswave.core.util.categories
import com.example.newswave.interests.presentation.viewmodel.InterestsViewModel


@Composable
fun InterestScreen(
    modifier: Modifier=Modifier,
    viewModel: InterestsViewModel = hiltViewModel(),
    onSaveSuccess :(Boolean?)->Unit,
    isNewUser:Boolean?=false

){
    Log.d("intr ui state","${viewModel.uiState}")
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
            if (uiState.updateSuccessful){
                onSaveSuccess(isNewUser)
            }





                LazyColumn (
                    modifier = Modifier.weight(1f)
                ){
                    items(categories) {item->
                        InterestItem(label = item, isChecked = uiState.interestsList.contains(item),
                            onCheck = { interest -> viewModel.interestClicked(interest) })
                    }
                }
                Button(
                    onClick = { viewModel.updateInterests() },
                    modifier=modifier.fillMaxWidth().height(48.dp),
                    enabled = uiState.previousInterests.toSet() != uiState.interestsList.toSet()
                ) {
                    Box (
                        contentAlignment = Alignment.Center
                    ){
                        if (uiState.isLoading && uiState.previousInterests.toSet() != uiState.interestsList.toSet())
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        else
                            Text(text = "Save")
                    }

                }



        }
    }
}



@Composable
fun InterestItem(
    modifier: Modifier=Modifier,
    label:String,
    isChecked:Boolean,
    onCheck:(String)->Unit
){
    var checked by remember(key1 = isChecked) { mutableStateOf(isChecked) }

    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)

    ){
        Text(
            text = label,
            modifier
                .weight(1f)
                .padding(start = 8.dp)
        )
        Switch(
            checked = checked,
            onCheckedChange = {
                checked= !checked
                onCheck(label)
            }

        )


    }
}

