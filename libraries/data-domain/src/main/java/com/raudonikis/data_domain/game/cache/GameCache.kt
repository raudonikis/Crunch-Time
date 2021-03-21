package com.raudonikis.data_domain.game.cache

import com.raudonikis.data_domain.game.cache.daos.GameDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameCache @Inject constructor(
    private val gameDao: GameDao,
)