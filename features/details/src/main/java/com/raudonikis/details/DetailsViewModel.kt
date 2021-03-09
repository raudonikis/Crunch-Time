package com.raudonikis.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.raudonikis.data_domain.games.models.Game
import com.raudonikis.data_domain.games.models.GameStatus
import com.raudonikis.data_domain.games.repo.GamesRepository
import com.raudonikis.navigation.NavigationDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
    private val gamesRepository: GamesRepository,
) : ViewModel() {

    /**
     * States
     */
    private val detailsState: MutableStateFlow<DetailsState> =
        MutableStateFlow(DetailsState.Initial)

    /**
     * Observables
     */
    val detailsStateLiveData: LiveData<DetailsState> = detailsState.asLiveData(viewModelScope.coroutineContext)

    fun updateGameStatus(game: Game, status: GameStatus) {
        detailsState.value = DetailsState.StatusUpdating
        viewModelScope.launch(Dispatchers.IO) {
            gamesRepository.updateGameStatus(game.copy(status = status))
                .onSuccess {
                    detailsState.value = DetailsState.StatusUpdateSuccess
                }
                .onFailure {
                    detailsState.value = DetailsState.StatusUpdateFailure
                }
        }
    }
}