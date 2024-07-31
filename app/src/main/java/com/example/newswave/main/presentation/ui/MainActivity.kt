package com.example.newswave.main.presentation.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newswave.core.presentation.ui.components.LoadingLayer
import com.example.newswave.core.presentation.ui.components.NewsDetailsBottomSheet
import com.example.newswave.core.presentation.ui.theme.NewsWaveTheme
import com.example.newswave.main.presentation.ui.components.Auth
import com.example.newswave.main.presentation.ui.components.Home
import com.example.newswave.main.presentation.ui.components.NewsWaveNavHost
import com.example.newswave.main.presentation.ui.components.bottomBarRoutes
import com.example.newswave.main.presentation.ui.components.bottomNavigationItems
import com.example.newswave.main.presentation.ui.components.navigateSingleTopTo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            val navController = rememberNavController()
            var showBottomSheet by remember {
                mutableStateOf(false)
            }
            var isLoading by remember {
                mutableStateOf(false)
            }
            var newsLink by remember {
                mutableStateOf("")
            }
            var darkTheme by remember { mutableStateOf(false) }
            val context=this
            NewsWaveTheme(darkTheme = darkTheme) {
                Surface {
                    Box {

                        MainScreen(
                            navController = navController,
                            onItemClicked={link ->
                                showBottomSheet=true
                                newsLink=link

                            },
                            onThemeUpdated = {darkTheme = !darkTheme},
                            onLoadingStateChange ={it -> isLoading = it },
                            navigate = {route -> navController.navigate(route){
                                popUpTo(navController.graph.id){
                                    inclusive= true
                                }
                            }
                            },
                            onShareNews = {
                                shareNews(
                                    context = context,
                                    link = it
                                )
                            }
                        )
                        if (showBottomSheet) {
                            NewsDetailsBottomSheet(
                                onDismiss = { showBottomSheet=false },
                                link =newsLink
                            )
                        }
                        if (isLoading){
                            LoadingLayer()
                        }
                    }
                }
            }
        }


    }

    @Composable
    fun MainScreen(
        navController: NavHostController,
        onItemClicked:(String)->Unit,
        onThemeUpdated:()->Unit,
        onLoadingStateChange :(Boolean)->Unit,
        navigate:(String)->Unit,
        onShareNews :(String)->Unit
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val listState = rememberLazyListState()
        val showBottomBar by remember {
            derivedStateOf {
                listState.firstVisibleItemIndex != 0
            }
        }

        Scaffold(
            bottomBar = {
                if (currentRoute in bottomBarRoutes) {
                    AnimatedVisibility(visible = !showBottomBar, enter = fadeIn(), exit = fadeOut()) {
                        NavigationBar {
                            bottomNavigationItems.forEach { item ->
                                NavigationBarItem(
                                    icon = { Icon(item.icon, contentDescription = null) },
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
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)){
                NewsWaveNavHost(
                    navController,
                    onItemClicked,
                    {
                        navController.navigateSingleTopTo(Home.route)
                    },
                    onThemeUpdated = onThemeUpdated,
                    onLoadingStateChange=onLoadingStateChange,
                    navigate = navigate,
                    onShareNews= onShareNews
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





}
