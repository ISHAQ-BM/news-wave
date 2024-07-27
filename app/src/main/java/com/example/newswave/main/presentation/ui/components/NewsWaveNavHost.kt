package com.example.newswave.main.presentation.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newswave.auth.presentation.ui.composables.AuthScreen
import com.example.newswave.bookmark.presentation.ui.composables.BookmarkScreen
import com.example.newswave.home.presentation.ui.composables.HomeScreen
import com.example.newswave.search.presentation.ui.composables.SearchScreen
import com.example.newswave.settings.presentation.ui.composables.SettingsScreen

@Composable
fun NewsWaveNavHost(
    navHostController: NavHostController,
    onItemClicked:(String)->Unit,
    modifier: Modifier=Modifier
) {
    NavHost(
        navController = navHostController,
        startDestination = Home.route,
        modifier = modifier
    ) {
        composable(Auth.route) {
            AuthScreen(
                onNavigateToHome = { navHostController.navigateSingleTopTo(Home.route) }
            )
        }
        composable(Home.route) { HomeScreen(onItemClicked = onItemClicked) }
        composable(Search.route) { SearchScreen(navController = navHostController) }
        composable(Bookmark.route) { BookmarkScreen(navController = navHostController) }
        composable(Settings.route) { SettingsScreen(navController = navHostController) }

    }
}
fun NavHostController.navigateSingleTopTo(route:String){
    this.navigate(route){launchSingleTop=true}
}