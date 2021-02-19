package com.raudonikis.androidskeleton

import androidx.navigation.NavController
import com.raudonikis.navigation.NavigationCommand
import com.raudonikis.navigation.NavigationDispatcher
import com.raudonikis.navigation.NavigationGraph
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@ActivityRetainedScoped
class NavigationHandler @Inject constructor(private val navigationDispatcher: NavigationDispatcher) {

    private lateinit var rootNavigationController: NavController
    private lateinit var currentNavigationController: NavController

    private fun setRootNavigationController(navController: NavController) {
        rootNavigationController = navController
        currentNavigationController = navController
    }

    fun setNavigationController(navController: NavController) {
        currentNavigationController = navController
    }

    suspend fun setUpNavigation(navController: NavController) {
        setRootNavigationController(navController)
        navigationDispatcher.getNavigationCommands()
            .onEach { navigationCommand ->
                Timber.d("NavigationCommand -> $navigationCommand")
                currentNavigationController.apply {
                    when (navigationCommand) {
                        is NavigationCommand.ToGraph -> {
                            onGraphNavigation(navigationCommand.graph)
                        }
                        is NavigationCommand.To -> {
                            navigate(navigationCommand.directions)
                        }
                        is NavigationCommand.Back -> {
                            popBackStack()
                        }
                        is NavigationCommand.BackTo -> {
                            popBackStack(navigationCommand.destinationId, true)
                        }
                        is NavigationCommand.ToRoot -> {
                            // TODO
                        }
                    }
                }
            }.collect()
    }

    private fun onGraphNavigation(graph: NavigationGraph) {
        val destination = when (graph) {
            is NavigationGraph.BottomNavigation -> {
                when (graph.inclusive) {
                    true -> R.id.action_global_bottomNavigationFragment_inclusive
                    false -> R.id.action_global_bottomNavigationFragment
                }
            }
        }
        rootNavigationController.navigate(destination)
    }
}
