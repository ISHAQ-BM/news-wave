package com.example.newswave.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newswave.R
import com.example.newswave.main.presentation.ui.components.Auth
import com.example.newswave.main.presentation.ui.components.Interests


@Composable
fun SettingsRoute(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    onThemeUpdated: () -> Unit,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by settingsViewModel.uiState.collectAsStateWithLifecycle()
    SettingsScreen(
        uiState = uiState,
        onThemeUpdated = onThemeUpdated,
        onNavigate = onNavigate,
        signOut = { settingsViewModel.signOut() },
        modifier = modifier
    )
}

@Composable
fun SettingsScreen(
    uiState: SettingsUiState,
    onThemeUpdated: () -> Unit,
    onNavigate: (String) -> Unit,
    signOut: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (uiState.isSignOut)
        onNavigate(Auth.route)
    Box {
        @OptIn(ExperimentalMaterial3Api::class)
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        titleContentColor = MaterialTheme.colorScheme.onBackground
                    ),
                    title = {
                        Text(
                            stringResource(id = R.string.settings),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                )
            },

            ) {
            Column(
                modifier = Modifier.padding(top = it.calculateTopPadding())
            ) {
                Settings(
                    modifier = modifier,
                    onThemeUpdated = onThemeUpdated,
                    onNavigate = onNavigate,
                    signOut = signOut

                )
            }

        }
        if (uiState.isLoading) {
            Box(

                Modifier
                    .fillMaxSize()
                    .background(Color(0x3D000000)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }


}

@Composable
fun Settings(
    modifier: Modifier = Modifier,
    onThemeUpdated: () -> Unit,
    onNavigate: (String) -> Unit,
    signOut: () -> Unit
) {
    Column(
        modifier.padding(horizontal = 16.dp)
    ) {

        SettingsListItem(
            icon = R.drawable.ic_account,
            settingText = stringResource(R.string.account),
            modifier = modifier,
            contentDescription = "",
            onClick = { onNavigate("") })
        HorizontalDivider()
        SettingsListItem(
            icon = R.drawable.ic_interests,
            settingText = stringResource(R.string.interests),
            modifier = modifier,
            contentDescription = "",
            onClick = { onNavigate(Interests.route) })
        HorizontalDivider()
        SettingsListItem(
            icon = R.drawable.ic_notification,
            settingText = stringResource(R.string.notifications),
            contentDescription = "",
            onClick = { onNavigate("") },
            modifier = modifier
        )
        HorizontalDivider()
        DarkMode(
            modifier = modifier,
            onThemeUpdated = onThemeUpdated
        )
        HorizontalDivider()
        SettingsListItem(
            icon = R.drawable.ic_privacy,
            settingText = stringResource(R.string.privacy_policy),
            modifier = modifier,
            contentDescription = "",
            onClick = { onNavigate("") })
        HorizontalDivider()
        SettingsListItem(
            icon = R.drawable.ic_about,
            settingText = stringResource(R.string.about),
            modifier = modifier,
            contentDescription = "",
            onClick = { onNavigate("") })
        HorizontalDivider()
        SettingsListItem(
            icon = R.drawable.ic_logout,
            settingText = stringResource(R.string.log_out),
            modifier = modifier,
            contentDescription = "",
            onClick = { signOut() })
        HorizontalDivider()

    }
}

@Composable
fun DarkMode(
    modifier: Modifier = Modifier,
    onThemeUpdated: () -> Unit
) {
    var checked by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)

    ) {
        Icon(painter = painterResource(R.drawable.ic_dark_mode), contentDescription = null)
        Text(
            text = stringResource(id = R.string.dark_mode),
            modifier
                .weight(1f)
                .padding(start = 8.dp)
        )
        Switch(
            checked = checked,
            onCheckedChange = {
                checked = !checked
                onThemeUpdated()
            }

        )


    }
}

@Composable
fun SettingsListItem(
    modifier: Modifier = Modifier,
    icon: Int,
    settingText: String,
    contentDescription: String,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(vertical = 16.dp)

    ) {
        Icon(painter = painterResource(icon), contentDescription = contentDescription)
        Text(
            text = settingText,
            modifier
                .weight(1f)
                .padding(start = 8.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_chevron_next),
            contentDescription = "",
        )
    }
}

