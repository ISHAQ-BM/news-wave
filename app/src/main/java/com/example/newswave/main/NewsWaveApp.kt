package com.example.newswave.main

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.newswave.core.presentation.ui.components.NewsDetailsBottomSheet
import com.example.newswave.main.navigation.NewsWaveNavHost
import com.example.newswave.main.navigation.bottomBarRoutes
import com.example.newswave.main.navigation.bottomNavigationItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsWaveApp(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onUpdateTheme: () -> Unit
) {
    val context = LocalContext.current
    var showBottomSheet by remember {
        mutableStateOf(false)
    }
    var link by remember {
        mutableStateOf("")
    }
    val sheetState = rememberModalBottomSheetState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    Scaffold(
        bottomBar = {
            if (currentRoute in bottomBarRoutes) {
                NavigationBar {
                    bottomNavigationItems.forEach { item ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    painter = painterResource(id = if (currentRoute == item.route) item.selectedIcon else item.unselectedIcon),
                                    contentDescription = null
                                )
                            },
                            label = { Text(item.label) },
                            selected = currentRoute == item.route,
                            onClick = {
                                if (currentRoute != item.route) {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.startDestinationId)
                                        launchSingleTop = true
                                    }
                                }
                            }
                        )
                    }
                }
            }


        }
    ) { innerPadding ->
        NewsWaveNavHost(
            modifier = modifier
                .padding(bottom = innerPadding.calculateBottomPadding())
                .fillMaxSize(),
            navController = navController,
            onClickNews = {
                link = it
                showBottomSheet = true
            },
            onUpdateTheme = onUpdateTheme,
            onShareNews = { link -> shareNews(context, link) }
        )
        if (showBottomSheet) {
            NewsDetailsBottomSheet(
                onDismiss = { showBottomSheet = false },
                sheetState = sheetState,
                link = link
            )
        }

    }


}

private fun shareNews(context: Context, link: String) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, link)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}