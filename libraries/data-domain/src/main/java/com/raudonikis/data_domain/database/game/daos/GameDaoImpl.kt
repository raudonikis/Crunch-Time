package com.raudonikis.data_domain.database.game.daos

import com.raudonikis.common.extensions.Outcome
import com.raudonikis.data_domain.game.models.Game
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameDaoImpl @Inject constructor() : GameDao {

    /**
     * Data
     */
    private var popularGames: MutableStateFlow<Outcome<List<Game>>> =
        MutableStateFlow(Outcome.empty())

    /**
     * Popular games
     */
    override fun getPopularGames(): Flow<Outcome<List<Game>>> {
        return popularGames
    }

    override fun setPopularGamesOutcome(outcome: Outcome<List<Game>>) {
        popularGames.value = outcome
    }

    override suspend fun updatePopularGames(games: List<Game>) {
        popularGames.value = Outcome.success(games)
    }
}