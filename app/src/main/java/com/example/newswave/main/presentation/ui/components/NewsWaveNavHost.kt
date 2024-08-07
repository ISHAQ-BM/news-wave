package com.example.newswave.main.presentation.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newswave.auth.presentation.ui.composables.AuthScreen
import com.example.newswave.bookmark.presentation.ui.composables.BookmarkScreen
import com.example.newswave.home.presentation.ui.composables.HomeScreen
import com.example.newswave.interests.presentation.ui.components.InterestScreen
import com.example.newswave.search.presentation.ui.composables.SearchScreen
import com.example.newswave.settings.presentation.ui.composables.SettingsScreen

@Composable
fun NewsWaveNavHost(
    navHostController: NavHostController,
    onItemClicked:(String)->Unit,
    onSaveSuccess :()->Unit,
    onThemeUpdated :()->Unit,
    onLoadingStateChange :(Boolean)->Unit,
    navigate:(String)->Unit,
    onShareNews :(String)-> Unit,
    modifier: Modifier=Modifier
) {
    NavHost(
        navController = navHostController,
        startDestination = Auth.route,
        modifier = modifier
    ) {
        composable(Auth.route) {
            AuthScreen(
                onLoginSuccess = { isNewUser ->
                        if (isNewUser){
                            navHostController.navigateSingleTopTo(Interests.route)
                        }else{
                            navHostController.navigateSingleTopTo(Home.route)
                        }

                }
            )
        }
        composable(Home.route) { HomeScreen(onItemClicked = onItemClicked,onShareNews=onShareNews) }
        composable(Search.route) { SearchScreen(onItemClicked = onItemClicked,onShareNews=onShareNews) }
        composable(Bookmark.route) { BookmarkScreen(onItemClicked = onItemClicked,onShareNews=onShareNews) }
        composable(Settings.route) { SettingsScreen(onThemeUpdated =onThemeUpdated ,onLoadingStateChange=onLoadingStateChange, navigate = navigate) }
        composable(Interests.route) { InterestScreen(onSaveSuccess = onSaveSuccess) }

    }
}
fun NavHostController.navigateSingleTopTo(route:String){
    this.navigate(route){launchSingleTop=true}
}