package com.example.newswave.settings.presentation.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.newswave.R

@Composable
fun SettingsScreen(
    navController:NavHostController
){
        @OptIn(ExperimentalMaterial3Api::class)
        (Scaffold(
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
                modifier = Modifier.padding(it.calculateTopPadding())
            ){
                Settings()
            }

    })

}

@Composable
fun Settings(
){
    Column {

        SettingsListItem(icon = R.drawable.ic_notification, settingText ="Profile" , contentDescription ="" , navigate = {})
        HorizontalDivider()
        SettingsListItem(icon = R.drawable.ic_notification, settingText ="Account" , contentDescription ="", navigate = {} )
        HorizontalDivider()
        SettingsListItem(icon = R.drawable.ic_interests, settingText ="Interests" , contentDescription ="" , navigate = {})
        HorizontalDivider()
        SettingsListItem(icon = R.drawable.ic_notification, settingText ="Notifications" , contentDescription ="" , navigate = {})
        HorizontalDivider()
        DarkMode()
        HorizontalDivider()
        SettingsListItem(icon = R.drawable.ic_notification, settingText ="Terms & Conditions" , contentDescription ="" , navigate = {})
        HorizontalDivider()
        SettingsListItem(icon = R.drawable.ic_notification, settingText ="About" , contentDescription ="" , navigate = {})
        HorizontalDivider()
        SettingsListItem(icon = R.drawable.ic_notification, settingText ="Log Out" , contentDescription ="", navigate = {} )
        HorizontalDivider()

    }
}

@Composable
fun DarkMode(
    modifier: Modifier = Modifier,
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
                checked = it
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
    navigate :() -> Unit
){
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .clickable {
                navigate
            }

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