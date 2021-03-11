package com.raudonikis.profile.collection

import com.raudonikis.data_domain.games.models.Game

sealed class GameCollectionState {
    object Initial : GameCollectionState()
    object Loading : GameCollectionState()
    data class Success(val games: List<Game>) : GameCollectionState()
    object Failure : GameCollectionState()
}