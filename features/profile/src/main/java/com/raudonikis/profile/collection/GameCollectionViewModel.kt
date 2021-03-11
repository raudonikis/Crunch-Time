package com.raudonikis.profile.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raudonikis.data_domain.games.models.Game
import com.raudonikis.data_domain.games.models.GameStatus
import com.raudonikis.data_domain.games.repo.GamesRepository
import com.raudonikis.navigation.NavigationDispatcher
import com.raudonikis.profile.ProfileRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GameCollectionViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
    private val gamesRepository: GamesRepository,
) : ViewModel() {

    private lateinit var currentGameStatus: GameStatus

    /**
     * States
     */
    private val _gameCollectionState: MutableStateFlow<GameCollectionState> =
        MutableStateFlow(GameCollectionState.Initial)

    /**
     * Observables
     */
    val gameCollectionState: StateFlow<GameCollectionState> = _gameCollectionState

    /**
     * Initialisation
     */
    fun onCreate(gameStatus: GameStatus) {
        currentGameStatus = gameStatus
        Timber.d("Game status -> $gameStatus")
        updateGameCollection()
    }

    /**
     * Game Collection
     */
    private fun updateGameCollection() {
        _gameCollectionState.value = GameCollectionState.Loading
        viewModelScope.launch {
            gamesRepository.getGameCollection(currentGameStatus)
                .onSuccess {
                    _gameCollectionState.value = GameCollectionState.Success(it)
                }
                .onFailure {
                    _gameCollectionState.value = GameCollectionState.Failure
                }
        }
    }

    /**
     * Navigation
     */
    fun navigateToDetails(game: Game) {
        navigationDispatcher.navigate(ProfileRouter.gameCollectionToDetails(game))
    }
}