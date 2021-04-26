package com.raudonikis.profile

import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game_status.GameStatus
import com.raudonikis.profile.collection.GameCollectionFragmentDirections
import com.raudonikis.profile.followers.FollowerType

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

    fun profileToFollowers(followerType: FollowerType) =
        ProfileFragmentDirections.actionProfileFragmentToFollowersFragment(followerType)

    /**
     * [GameCollectionFragment] routes
     */
    fun gameCollectionToDetails(game: Game) =
        GameCollectionFragmentDirections.actionGameCollectionFragmentToNavigationDetails(game)
}