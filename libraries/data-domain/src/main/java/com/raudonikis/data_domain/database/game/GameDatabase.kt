package com.raudonikis.data_domain.database.game

import com.raudonikis.data_domain.database.game.daos.GameDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameDatabase @Inject constructor(
    private val gameDao: GameDao,
) {

    fun gameDao(): GameDao = gameDao
}