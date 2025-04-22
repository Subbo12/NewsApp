package com.example.newsapp.navigation

sealed class Screen(val route: String) {
    object Home : Screen("Home")
    object Search : Screen("Search")
    object Favorites : Screen("Favorites")
}