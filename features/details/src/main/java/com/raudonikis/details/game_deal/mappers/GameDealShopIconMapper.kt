package com.raudonikis.details.game_deal.mappers

import androidx.annotation.DrawableRes
import com.raudonikis.data_domain.game_deal.GameDealShop
import com.raudonikis.details.R

object GameDealShopIconMapper {

    val gameDealShopMap = mapOf(
        "humblestore" to R.drawable.ic_store_humble_store,
        "humblewidgets" to R.drawable.ic_store_humble_store,
        "gog" to R.drawable.ic_store_gog,
        "microsoft" to R.drawable.ic_store_microsoft_store,
        "steam" to R.drawable.ic_store_steam,
        "wingamestore" to R.drawable.ic_store_winstore,
        "greenmangaming" to R.drawable.ic_store_green_man_gaming,
        "gamesload" to R.drawable.ic_store_gamesload,
        "dlgamer" to R.drawable.ic_store_dlgamer,
        "gamebillet" to R.drawable.ic_store_games_billet,
        "gamesplanetfr" to R.drawable.ic_store_games_planet,
        "gamesplanetde" to R.drawable.ic_store_games_planet,
        "gamesplanet" to R.drawable.ic_store_games_planet,
        "game2" to R.drawable.ic_store_2_game,
        "voidu" to R.drawable.ic_store_voidu,
        "allyouplay" to R.drawable.ic_store_all_you_play,
        "gamersgate" to R.drawable.ic_store_gamesgate,
        "epic" to R.drawable.ic_store_epic_games,
    )

    @DrawableRes
    fun iconFromGameDealShop(gameDealShop: GameDealShop): Int {
        return gameDealShopMap[gameDealShop.id] ?: R.drawable.ic_store_placeholder
    }
}