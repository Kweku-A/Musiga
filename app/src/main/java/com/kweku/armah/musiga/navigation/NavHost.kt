package com.kweku.armah.musiga.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kweku.armah.musiga.navigation.routes.NavRoutes
import com.kweku.armah.ui.screen.FeedScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.Feed.route,
    ) {
        val navigateBack: () -> Unit = { navController.navigateUp() }

        composable(NavRoutes.Feed.route) {
            FeedScreen(
                navigateBack = navigateBack,
            )
        }
    }
}