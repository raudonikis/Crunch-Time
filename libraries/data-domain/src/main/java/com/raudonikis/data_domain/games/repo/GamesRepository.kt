package com.raudonikis.data_domain.games.repo

import com.raudonikis.data_domain.games.mappers.GameModelMapper
import com.raudonikis.data_domain.games.models.GameModel
import com.raudonikis.network.GamesApi
import com.raudonikis.network.utils.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GamesRepository @Inject constructor(private val gamesApi: GamesApi) {

    suspend fun search(name: String): NetworkResponse<List<GameModel>> {
        return withContext(Dispatchers.IO) {
            gamesApi.search(name)
                .map {
                    GameModelMapper.fromGameSearchResponseList(it)
                }
        }
    }
}