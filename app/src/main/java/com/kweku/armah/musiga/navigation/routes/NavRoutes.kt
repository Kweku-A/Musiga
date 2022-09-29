package com.kweku.armah.musiga.navigation.routes

sealed class NavRoutes(val route: String) {
    object Feed : NavRoutes("feed")
}
