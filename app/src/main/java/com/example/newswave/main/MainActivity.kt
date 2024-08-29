package com.example.newswave.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.newswave.core.presentation.ui.theme.NewsWaveTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            val navController = rememberNavController()
            var darkTheme by remember { mutableStateOf(false) }

            NewsWaveTheme(darkTheme = darkTheme) {
                NewsWaveApp(
                    navController = navController,
                    onUpdateTheme = { darkTheme = !darkTheme }
                )
            }
        }
    }
}
