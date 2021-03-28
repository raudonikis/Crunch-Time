package com.raudonikis.data_domain.game_deal.mappers

import com.raudonikis.data_domain.game_deal.GameDealShop
import com.raudonikis.network.game_deals.DealShopResponse

object GameDealShopMapper {

    fun fromDealShopResponse(response: DealShopResponse): GameDealShop {
        return GameDealShop(
            id = response.id,
            name = response.name
        )
    }
}