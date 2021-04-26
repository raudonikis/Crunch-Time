package com.raudonikis.bottomnavigation

import android.content.Intent
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
class NavigationHandler @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher
) {

    private lateinit var rootNavigationController: NavController
    private lateinit var currentNavigationController: NavController

    private fun setRootNavigationController(navController: NavController) {
        rootNavigationController = navController
        currentNavigationController = navController
    }

    fun setNavigationController(navController: NavController) {
        currentNavigationController = navController
    }

    suspend fun setUpNavigation(navController: NavController, onIntent: (Intent) -> Unit = {}) {
        setRootNavigationController(navController)
        navigationDispatcher.getNavigationCommands()
            .onEach { navigationCommand ->
                Timber.d("NavigationCommand -> $navigationCommand")
                Timber.d("CurrentDestination -> ${navController.currentDestination}")
                currentNavigationController.apply {
                    when (navigationCommand) {
                        is NavigationCommand.ToGraph -> {
                            onGraphNavigation(navigationCommand.graph, navController)
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
                        is NavigationCommand.ToUri -> {
                            val uriIntent = Intent(Intent.ACTION_VIEW).apply {
                                data = navigationCommand.uri
                            }
                            onIntent(uriIntent)
                        }
                    }
                }
            }.collect()
    }

    private fun onGraphNavigation(graph: NavigationGraph, navController: NavController) {
        val destination = when (graph) {
            is NavigationGraph.BottomNavigation -> {
                when (graph.inclusive) {
                    true -> R.id.action_global_bottomNavigationFragment_inclusive
                    false -> R.id.action_global_bottomNavigationFragment
                }
            }
            is NavigationGraph.Login -> {
                setNavigationController(navController)
                R.id.action_global_navigation_login
            }
        }
        rootNavigationController.navigate(destination)
    }
}
