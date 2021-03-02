package com.raudonikis.home

import androidx.lifecycle.ViewModel
import com.raudonikis.navigation.NavigationDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
) : ViewModel() {

    fun getGames() {
        /*viewModelScope.launch {
            authenticationRepository.updateAccessToken()
            authenticationRepository.getGames().map {
                Timber.d(it.toString())
            }
        }*/
    }

    fun navigateToHomeNext() {
        navigationDispatcher.navigate(HomeRouter.homeToHomeNext())
    }
}