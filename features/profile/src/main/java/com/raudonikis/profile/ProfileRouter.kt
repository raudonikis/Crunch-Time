package com.raudonikis.profile

import com.raudonikis.data_domain.games.models.Game
import com.raudonikis.data_domain.games.models.GameStatus
import com.raudonikis.profile.collection.GameCollectionFragmentDirections

/**
 * Router for the Profile module
 */
object ProfileRouter {

    /**
     * [ProfileFragment] routes
     */
    fun profileToGameCollection(gameStatus: GameStatus) =
        ProfileFragmentDirections.actionProfileFragmentToGameCollectionFragment(gameStatus)

    fun profileToDetails(game: Game) =
        ProfileFragmentDirections.actionProfileFragmentToNavigationDetails(game)

    /**
     * [GameCollectionFragment] routes
     */
    fun gameCollectionToDetails(game: Game) =
        GameCollectionFragmentDirections.actionGameCollectionFragmentToNavigationDetails(game)
}