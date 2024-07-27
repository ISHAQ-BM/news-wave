package com.example.newswave.main.presentation.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

interface NewsWaveDestination{
    val icon: ImageVector
    val route: String
    val label:String
}



object Home : NewsWaveDestination {
    override val icon= Icons.Filled.Home
    override val route="home"
    override val label="Home"

}

object Search : NewsWaveDestination {
    override val icon= Icons.Filled.Search
    override val route="search"
    override val label="Search"

}
object Bookmark : NewsWaveDestination {
    override val icon= Icons.Filled.Star
    override val route="bookmark"
    override val label="Bookmark"

}

object Settings : NewsWaveDestination {
    override val icon=Icons.Filled.Settings
    override val route="settings"
    override val label="Settings"

}

object Auth : NewsWaveDestination {
    override val icon=Icons.Filled.Home
    override val route="auth"
    override val label="Auth"

}

val bottomNavigationItems = listOf(
    Home,
    Search,
    Bookmark,
    Settings
)

val bottomBarRoutes = setOf("home", "search","bookmark","settings")
