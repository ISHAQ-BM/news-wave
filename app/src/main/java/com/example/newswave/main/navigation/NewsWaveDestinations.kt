package com.example.newswave.main.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.newswave.R

interface NewsWaveDestination {
    val selectedIcon: Int
    val unselectedIcon: Int
    val route: String
    val label: String
}

object Home : NewsWaveDestination {
    override val selectedIcon: Int = R.drawable.ic_home_contained
    override val unselectedIcon: Int = R.drawable.ic_home_outlined
    override val route = "home"
    override val label = "Home"

}

object Search : NewsWaveDestination {
    override val selectedIcon: Int = R.drawable.ic_search_contained
    override val unselectedIcon: Int = R.drawable.ic_search_outlined
    override val route = "search"
    override val label = "Search"

}

object Bookmark : NewsWaveDestination {
    override val selectedIcon: Int = R.drawable.ic_bookmark_contained
    override val unselectedIcon: Int = R.drawable.ic_bookmark_outlined
    override val route = "bookmark"
    override val label = "Bookmark"

}

object Settings : NewsWaveDestination {
    override val selectedIcon: Int = R.drawable.ic_settings_contained
    override val unselectedIcon: Int = R.drawable.ic_settings_outlined
    override val route = "settings"
    override val label = "Settings"

}

object Auth : NewsWaveDestination {
    override val selectedIcon: Int = 1
    override val unselectedIcon: Int = 1
    override val route = "auth"
    override val label = "Auth"

}

object Interests : NewsWaveDestination {
    override val selectedIcon: Int = 1

    override val unselectedIcon: Int = 1
    override val route = "interests"
    override val label = "Interests"
    const val isNewUserArg = "is_new_user"
    val routeWithArgs = "$route/{$isNewUserArg}"
    val arguments = listOf(
        navArgument(isNewUserArg) { type = NavType.BoolType }
    )

}

val bottomNavigationItems = listOf(
    Home,
    Search,
    Bookmark,
    Settings
)

val bottomBarRoutes = setOf("home", "search", "bookmark", "settings")
