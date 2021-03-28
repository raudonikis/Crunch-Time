package com.raudonikis.data_domain.game_deal.mappers

import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game_deal.GameDeal
import com.raudonikis.network.game_deals.DealSearchResponse

object GameDealMapper {

    fun fromDealSearchResponseList(
        responses: List<DealSearchResponse>,
        game: Game
    ): List<GameDeal> {
        return responses.filter { dealSearchResponse ->
            dealSearchResponse.name == game.name
        }.flatMap {
            fromDealSearchResponse(it)
        }
    }

    fun fromDealSearchResponse(response: DealSearchResponse): List<GameDeal> {
        return response.deals.map {
            GameDeal(
                priceNew = it.newPrice,
                priceOld = it.oldPrice,
                priceCut = it.priceCut,
                shop = GameDealShopMapper.fromDealShopResponse(it.shop)
            )
        }
    }
}