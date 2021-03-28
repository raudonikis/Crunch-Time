package com.raudonikis.navigation

import android.net.Uri
import androidx.navigation.NavDirections
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationDispatcher @Inject constructor() {
    private val coroutineScope = CoroutineScope(Job() + Dispatchers.IO)
    private val navigationCommands = MutableSharedFlow<NavigationCommand>()

    fun getNavigationCommands(): SharedFlow<NavigationCommand> = navigationCommands

    fun navigate(destination: NavDirections) {
        coroutineScope.launch {
            navigationCommands.emit(NavigationCommand.To(destination))
        }
    }

    fun navigate(graph: NavigationGraph) {
        coroutineScope.launch {
            navigationCommands.emit(NavigationCommand.ToGraph(graph))
        }
    }

    fun navigate(uri: Uri) {
        coroutineScope.launch {
            navigationCommands.emit(NavigationCommand.ToUri(uri))
        }
    }

    fun navigateBack() {
        coroutineScope.launch {
            navigationCommands.emit(NavigationCommand.Back)
        }
    }
}