package com.raudonikis.details.game_deal.mappers

import androidx.annotation.DrawableRes
import com.raudonikis.data_domain.game_deal.GameDealShop
import com.raudonikis.details.R

object GameDealShopIconMapper {

    val gameDealShopMap = mapOf(
        "humblestore" to R.drawable.ic_humble_store,
        "humblewidgets" to R.drawable.ic_humble_store,
        "gog" to R.drawable.ic_gog,
        "microsoft" to R.drawable.ic_microsoft_store,
        "steam" to R.drawable.ic_steam,
    )

    @DrawableRes
    fun iconFromGameDealShop(gameDealShop: GameDealShop): Int? {
        return gameDealShopMap[gameDealShop.id]
    }
}