package com.example.newswave.interests.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newswave.R
import com.example.newswave.core.util.categories


@Composable
fun InterestsRoute(
    interestsViewModel: InterestsViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    showBackButton: Boolean,
    onBackClick: () -> Unit,
    showSkipButton: Boolean,
    onSkipClick: () -> Unit,
) {

    val uiState by interestsViewModel.uiState.collectAsStateWithLifecycle()
    if (uiState.saveSuccessful && showSkipButton)
        onSkipClick()
    InterestScreen(
        uiState = uiState,
        modifier = modifier,
        onSaveInterests = { interestsViewModel.saveInterests() },
        onInterestToggled = { interest, isChecked ->
            interestsViewModel.interestClicked(
                interest,
                isChecked
            )
        },
        showBackButton = showBackButton,
        onBackClick = onBackClick,
        showSkipButton = showSkipButton,
        onSkipClick = onSkipClick

    )
}


@Composable
fun InterestScreen(
    uiState: InterestsUiState,
    modifier: Modifier = Modifier,
    onSaveInterests: () -> Unit,
    onInterestToggled: (String, Boolean) -> Unit,
    showBackButton: Boolean,
    onBackClick: () -> Unit,
    showSkipButton: Boolean,
    onSkipClick: () -> Unit
) {
    Scaffold(
        topBar = {
            InterestsTopAppBar(
                showBackButton = showBackButton,
                onBackClick = onBackClick,
                showSkipButton = showSkipButton,
                onSkipClick = onSkipClick
            )
        }
    ) {
        Column(
            modifier = modifier
                .padding(top = it.calculateTopPadding(), end = 16.dp, start = 16.dp, bottom = 16.dp)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {

                item {
                    Text(
                        text = stringResource(R.string.choose_your_interests),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = stringResource(R.string.choose_which),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }


                items(categories) { item ->
                    InterestItem(
                        label = item, isChecked = uiState.modifiedInterests.contains(item),
                        onToggle = onInterestToggled
                    )
                }
            }
            InterestButton(
                label = if (showSkipButton) stringResource(id = R.string.Continue) else stringResource(
                    id = R.string.save
                ),
                enabled = uiState.originalInterests != uiState.modifiedInterests,
                isLoading = uiState.isLoading && uiState.originalInterests != uiState.modifiedInterests,
                onClick = onSaveInterests
            )


        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterestsTopAppBar(
    showBackButton: Boolean,
    onBackClick: () -> Unit,
    showSkipButton: Boolean,
    onSkipClick: () -> Unit,
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
        ),
        title = {
            if (showBackButton)
                Text(text = stringResource(id = R.string.interests))
        },
        navigationIcon = {
            if (showBackButton)
                IconButton(onClick = { onBackClick() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
        },
        actions = {
            if (showSkipButton)
                TextButton(onClick = { onSkipClick() }) {
                    Text(
                        text = stringResource(R.string.skip),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
        }

    )

}

@Composable
fun InterestButton(
    label: String,
    enabled: Boolean,
    isLoading: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { onClick() },
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        enabled = enabled
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            if (isLoading)
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            else
                Text(text = label)
        }

    }

}


@Composable
fun InterestItem(
    modifier: Modifier = Modifier,
    label: String,
    isChecked: Boolean,
    onToggle: (String, Boolean) -> Unit
) {
    var checked by remember(key1 = isChecked) { mutableStateOf(isChecked) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)

    ) {
        Text(
            text = label,
            modifier
                .weight(1f)
                .padding(start = 8.dp)
        )
        Switch(
            checked = checked,
            onCheckedChange = {
                checked = !checked
                onToggle(label, checked)
            }

        )


    }
}


