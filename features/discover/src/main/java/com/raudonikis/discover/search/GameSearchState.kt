package com.raudonikis.discover.search

import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.game.models.Game

sealed class GameSearchState {
    object Disabled : GameSearchState()
    data class GameSearch(val outcome: Outcome<List<Game>>) : GameSearchState()
}
