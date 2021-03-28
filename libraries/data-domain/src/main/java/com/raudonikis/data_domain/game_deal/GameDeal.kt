package com.raudonikis.data_domain.game_deal

data class GameDeal(
    val priceNew: Double,
    val priceOld: Double,
    val url: String? = null,
    val priceCut: Double,
    val shop: GameDealShop,
)
