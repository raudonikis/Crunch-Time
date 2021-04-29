package com.raudonikis.navigation

import androidx.core.net.toUri
import androidx.navigation.NavDirections
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationDispatcher @Inject constructor() {
    private val navigationCommands = Channel<NavigationCommand>(Channel.BUFFERED)

    fun getNavigationCommands(): Flow<NavigationCommand> = navigationCommands.receiveAsFlow()

    fun navigate(destination: NavDirections) {
        navigationCommands.offer(NavigationCommand.To(destination))
    }

    fun navigate(graph: NavigationGraph) {
        navigationCommands.offer(NavigationCommand.ToGraph(graph))
    }

    fun navigate(uriString: String) {
        navigationCommands.offer(NavigationCommand.ToUri(uriString.toUri()))
    }

    fun navigateBack() {
        navigationCommands.offer(NavigationCommand.Back)
    }
}