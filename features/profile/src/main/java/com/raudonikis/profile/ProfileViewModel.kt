package com.raudonikis.profile

import androidx.lifecycle.ViewModel
import com.raudonikis.data_domain.games.models.GameStatus
import com.raudonikis.navigation.NavigationDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
) : ViewModel() {

    /**
     * Navigation
     */
    fun navigateToGameCollection(gameStatus: GameStatus) {
        navigationDispatcher.navigate(ProfileRouter.profileToGameCollection(gameStatus))
    }
}