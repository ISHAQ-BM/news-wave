package com.example.newswave.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newswave.auth.ui.AuthRoute
import com.example.newswave.bookmark.ui.BookmarkRoute
import com.example.newswave.home.presentation.HomeRoute
import com.example.newswave.interests.ui.InterestsRoute
import com.example.newswave.search.ui.SearchRoute
import com.example.newswave.settings.ui.SettingsRoute

@Composable
fun NewsWaveNavHost(
    navController: NavHostController,
    onClickNews: (String) -> Unit,
    onUpdateTheme: () -> Unit,
    onShareNews: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Home.route,
        modifier = modifier
    ) {
        composable(Auth.route) {
            AuthRoute(
                onLoginSuccess = { isNewUser ->
                    if (isNewUser) {
                        navController.navigate("${Interests.route}/$isNewUser")
                    } else {
                        navController.navigateSingleTopTo(Home.route)
                    }

                })
        }
        composable(Home.route) {
            HomeRoute(onClickNews = onClickNews, onShareNews = onShareNews)
        }
        composable(Search.route) {
            SearchRoute(onClickNews = onClickNews, onShareNews = onShareNews)
        }
        composable(Bookmark.route) {
            BookmarkRoute(
                onClickNews = onClickNews,
                onShareNews = onShareNews,
                navigateToHome = {
                    navController.navigate(Home.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(Settings.route) {
            SettingsRoute(
                onThemeUpdated = onUpdateTheme,
                onNavigate = { route ->
                    when (route) {
                        Interests.route -> {
                            val isNewUser = false
                            navController.navigate("${Interests.route}/$isNewUser")

                        }

                        Auth.route -> {
                            navController.navigate(route) {
                                popUpTo(navController.graph.id) {
                                    inclusive = true
                                }
                            }
                        }

                        else -> {
                            navController.navigate(route)
                        }
                    }
                }
            )
        }
        composable(
            route = Interests.routeWithArgs,
            arguments = Interests.arguments
        ) { navBackStackEntry ->
            val isNewUser =
                navBackStackEntry.arguments?.getBoolean(Interests.isNewUserArg)


            InterestsRoute(
                showBackButton = !isNewUser!!,
                onBackClick = { navController.popBackStack() },
                showSkipButton = isNewUser,
                onSkipClick = { navController.navigate(Home.route) },

                )

        }

    }
}

fun NavHostController.navigateSingleTopTo(route: String) {
    this.navigate(route) { launchSingleTop = true }
}
