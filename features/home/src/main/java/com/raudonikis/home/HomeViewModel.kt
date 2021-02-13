package com.raudonikis.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raudonikis.data_domain.repo.IgdbRepository
import com.raudonikis.navigation.NavigationDispatcher
import com.raudonikis.navigation.NavigationGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
    private val igdbRepository: IgdbRepository,
) : ViewModel() {

    fun getGames() {
        viewModelScope.launch {
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