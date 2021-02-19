package com.raudonikis.navigation

sealed class NavigationGraph {
    data class BottomNavigation(val inclusive: Boolean = false) : NavigationGraph()
    /*data class Home(val inclusive: Boolean = false) : NavigationGraph()
    object Discover : NavigationGraph()*/
}