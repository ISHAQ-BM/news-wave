package com.example.newswave.settings.presentation.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.newswave.R
import com.example.newswave.core.presentation.ui.theme.NewsWaveTheme
import com.example.newswave.main.presentation.ui.components.Auth
import com.example.newswave.main.presentation.ui.components.Interests
import com.example.newswave.settings.presentation.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    onThemeUpdated :()->Unit,
    onLoadingStateChange :(Boolean)->Unit,
    navigate:(String)->Unit
){
        @OptIn(ExperimentalMaterial3Api::class)
        Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Settings",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            )
        },

        ) {
            Column (
                modifier = Modifier.padding(top =it.calculateTopPadding())
            ){
                Settings(
                    onThemeUpdated = onThemeUpdated,
                    onLoadingStateChange = onLoadingStateChange,
                    navigate = navigate
                )
            }

    }

}

@Composable
fun Settings(
    modifier: Modifier=Modifier,
    onThemeUpdated: () -> Unit,
    onLoadingStateChange: (Boolean) -> Unit,
    navigate: (String) -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
){
    Column(
        modifier.padding(horizontal = 16.dp)
    ){

        val uiState by viewModel.uiState.collectAsState()
        onLoadingStateChange(uiState.isLoading)
        if (uiState.isSignOut)
            navigate(Auth.route)
        SettingsListItem(icon = R.drawable.ic_profile, settingText ="Profile" , contentDescription ="" , onClick = {})
        HorizontalDivider()
        SettingsListItem(icon = R.drawable.ic_account, settingText ="Account" , contentDescription ="", onClick = {} )
        HorizontalDivider()
        SettingsListItem(icon = R.drawable.ic_interests, settingText ="Interests" , contentDescription ="" , onClick = {navigate(Interests.route)})
        HorizontalDivider()
        SettingsListItem(icon = R.drawable.ic_notification, settingText ="Notifications" , contentDescription ="" , onClick = {})
        HorizontalDivider()
        DarkMode(onThemeUpdated=onThemeUpdated)
        HorizontalDivider()
        SettingsListItem(icon = R.drawable.ic_privacy, settingText ="Privacy policy" , contentDescription ="" , onClick = {})
        HorizontalDivider()
        SettingsListItem(icon = R.drawable.ic_about, settingText ="About" , contentDescription ="" , onClick = {})
        HorizontalDivider()
        SettingsListItem(icon = R.drawable.ic_logout, settingText ="Log Out" , contentDescription ="", onClick = {viewModel.signOut()} )
        HorizontalDivider()

    }
}

@Composable
fun DarkMode(
    modifier: Modifier = Modifier,
    onThemeUpdated: () -> Unit
){
    var checked by remember { mutableStateOf(false) }

    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)

    ){
        Icon(painter = painterResource(R.drawable.ic_dark_mode), contentDescription ="" )
        Text(
            text = "Dark Mode",
            modifier
                .weight(1f)
                .padding(start = 8.dp)
        )
        Switch(
            checked = checked,
            onCheckedChange = {
                checked= !checked
                onThemeUpdated()
            }

        )


    }
}

@Composable
fun SettingsListItem(
    modifier: Modifier = Modifier,
    icon:Int,
    settingText:String,
    contentDescription:String,
    onClick :() -> Unit
){
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(vertical = 16.dp)

    ){
        Icon(painter = painterResource(icon), contentDescription =contentDescription )
        Text(
            text = settingText,
            modifier
                .weight(1f)
                .padding(start = 8.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_chevron_next),
            contentDescription ="",

            )
    }
}

