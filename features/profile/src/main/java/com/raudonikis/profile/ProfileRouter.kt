package com.raudonikis.profile

import com.raudonikis.data_domain.games.models.GameStatus

object ProfileRouter {
    fun profileToGameCollection(gameStatus: GameStatus) =
        ProfileFragmentDirections.actionProfileFragmentToGameCollectionFragment(gameStatus)
}