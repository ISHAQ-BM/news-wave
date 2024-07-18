package com.example.newswave.main.presentation.ui.composables

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navArgument
import androidx.navigation.ui.setupWithNavController
import com.example.newswave.R
import com.example.newswave.bookmark.presentation.ui.composables.BookmarkScreen
import com.example.newswave.core.presentation.ui.theme.NewsWaveTheme

import com.example.newswave.home.presentation.ui.composables.HomeScreen
import com.example.newswave.news.presentation.viewmodel.NewsViewModel
import com.example.newswave.news_details.presentation.ui.composables.NewsDetailsScreen
import com.example.newswave.search.presentation.ui.composables.SearchScreen
import com.example.newswave.settings.presentation.ui.composables.SettingsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            val navController = rememberNavController()
            NewsWaveTheme {
                Surface {
                    MainScreen(navController = navController)
                }
            }
        }


    }

    val bottomNavigationItems = listOf(
        NavigationItem("home", "Home", Icons.Filled.Home ),
        NavigationItem("search", "Search", Icons.Filled.Search),
        NavigationItem("bookmark", "Bookmark", Icons.Filled.Star),
        NavigationItem("settings", "Settings", Icons.Filled.Settings)
    )

    val bottomBarRoutes = setOf("home", "search","bookmark","settings")

    @Composable
    fun MainScreen(navController: NavHostController) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        Scaffold(
            bottomBar = {
                if (currentRoute in bottomBarRoutes) {
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
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)){
                AppNavigation(navController)
            }
        }

    }

    data class NavigationItem(val route: String, val label: String, val icon: ImageVector)


    @Composable
    fun AppNavigation(
        navController:NavHostController,
    ){

        NavHost(navController = navController, startDestination = "home") {
            composable("home"){ HomeScreen(navController = navController)}
            composable("search"){ SearchScreen(navController = navController) }
            composable("bookmark"){ BookmarkScreen(navController = navController) }
            composable("settings"){ SettingsScreen(navController = navController) }
            composable("news_details/{link}", arguments = listOf(navArgument("link") { type = NavType.StringType })) { backStackEntry ->
                NewsDetailsScreen(backStackEntry.arguments?.getString("link") ?: "")
            }

        }
    }
}