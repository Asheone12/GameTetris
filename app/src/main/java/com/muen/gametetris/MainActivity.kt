package com.muen.gametetris

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.muen.gametetris.settings.SettingsHandler
import com.muen.gametetris.ui.screens.AboutScreen
import com.muen.gametetris.ui.screens.HomeScreen
import com.muen.gametetris.ui.screens.tetris.TetrisScreen
import com.muen.gametetris.ui.theme.AndroidTetrisTheme
import com.muen.gametetris.ui.theme.DarkColors
import com.muen.gametetris.ui.theme.LightColors
import com.muen.gametetris.ui.theme.LocalColors
import com.muen.gametetris.ui.theme.TetrisTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: handle the savedInstanceState here
        SettingsHandler.openSharedPreferences(this)
        setContent {
            AndroidTetrisTheme {
                val themeColors = if (isSystemInDarkTheme()) DarkColors else LightColors
                val theme = TetrisTheme(
                    colors = themeColors,
                    isDark = isSystemInDarkTheme()
                )
                CompositionLocalProvider(LocalColors provides theme) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = NavDestination.Home.route
                    ) {
                        composable(route = NavDestination.Home.route) { HomeScreen(navController = navController) }
                        composable(route = NavDestination.Tetris.route) { TetrisScreen() }
                        composable(route = NavDestination.About.route){ AboutScreen(navController = navController)}
                    }
                    navController.navigate(NavDestination.Home.route) {
                        launchSingleTop = true
                    }
                }
            }
        }
    }
}

enum class NavDestination(val route: String) {
    Home("home"),
    Tetris("tetris"),
    About("about")
}