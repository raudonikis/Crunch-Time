package com.raudonikis.navigation

sealed class NavigationGraph {
    data class BottomNavigation(val inclusive: Boolean = false) : NavigationGraph()
    object Login: NavigationGraph()
}