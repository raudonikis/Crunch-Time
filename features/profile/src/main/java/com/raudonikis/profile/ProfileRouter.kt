package com.raudonikis.profile

import com.raudonikis.data_domain.games.models.Game
import com.raudonikis.data_domain.games.models.GameStatus
import com.raudonikis.profile.collection.GameCollectionFragmentDirections

object ProfileRouter {
    fun profileToGameCollection(gameStatus: GameStatus) =
        ProfileFragmentDirections.actionProfileFragmentToGameCollectionFragment(gameStatus)

    fun gameCollectionToDetails(game: Game) =
        GameCollectionFragmentDirections.actionGameCollectionFragmentToNavigationDetails(game)
}