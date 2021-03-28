package com.raudonikis.details.game_deal

import com.raudonikis.data_domain.game_deal.GameDeal

sealed class DealsState {
    object Initial : DealsState()
    object Loading : DealsState()
    data class Success(val deals: List<GameDeal>) : DealsState()
    object Failure : DealsState()
}
