package com.raudonikis.data_domain.game_deal.usecases

import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game_deal.GameDeal
import com.raudonikis.data_domain.game_deal.mappers.GameDealMapper
import com.raudonikis.network.GamesApi
import javax.inject.Inject

class GameDealsUseCase @Inject constructor(
    private val gamesApi: GamesApi,
) {

    /**
     * Search for game deals
     */
    suspend fun searchGameDeals(game: Game): Outcome<List<GameDeal>> {
        return try {
            val response = gamesApi.searchGameDeals(game.name)
            Outcome.success(GameDealMapper.fromDealSearchResponseList(response, game))
        } catch (e: Exception) {
            Outcome.failure(e.message)
        }
    }
}