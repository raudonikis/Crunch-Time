package com.raudonikis.details.game_deal.mappers

import com.raudonikis.data_domain.game_deal.GameDeal
import com.raudonikis.details.game_deal.DealItem

object DealItemMapper {

    fun fromDeal(deal: GameDeal): DealItem {
        return DealItem(deal)
    }

    fun fromDealList(deals: List<GameDeal>): List<DealItem> {
        return deals.map { fromDeal(it) }
    }
}