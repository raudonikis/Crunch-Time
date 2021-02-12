package com.raudonikis.home

import androidx.lifecycle.ViewModel
import com.raudonikis.data_domain.repo.IgdbRepository
import com.raudonikis.navigation.NavigationDispatcher
import com.raudonikis.navigation.NavigationGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
    private val igdbRepository: IgdbRepository,
) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = SupervisorJob() + Dispatchers.IO

    fun getGames() {
        launch {
            igdbRepository.updateAccessToken()
            igdbRepository.getGames().map {
                Timber.d(it.toString())
            }
        }
    }

    fun navigateToDashboard() {
        navigationDispatcher.navigate(NavigationGraph.Dashboard)
    }

    fun navigateToHomeNext() {
        navigationDispatcher.navigate(HomeRouter.homeToHomeNext())
    }
}