package com.raudonikis.navigation

import android.net.Uri
import androidx.navigation.NavDirections

sealed class NavigationCommand {
    data class To(val directions: NavDirections) : NavigationCommand()
    data class ToGraph(val graph: NavigationGraph) : NavigationCommand()
    data class ToUri(val uri: Uri) : NavigationCommand()
    object Back : NavigationCommand()
    data class BackTo(val destinationId: Int) : NavigationCommand()
    object ToRoot : NavigationCommand()
}
