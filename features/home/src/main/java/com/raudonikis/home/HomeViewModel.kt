package com.raudonikis.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raudonikis.data_domain.repo.AuthenticationRepository
import com.raudonikis.navigation.NavigationDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
    private val authenticationRepository: AuthenticationRepository,
) : ViewModel() {

    fun getGames() {
        viewModelScope.launch {
            authenticationRepository.updateAccessToken()
            authenticationRepository.getGames().map {
                Timber.d(it.toString())
            }
        }
    }

    fun navigateToHomeNext() {
        navigationDispatcher.navigate(HomeRouter.homeToHomeNext())
    }
}