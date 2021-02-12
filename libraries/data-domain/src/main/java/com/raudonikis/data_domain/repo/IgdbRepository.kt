package com.raudonikis.data_domain.repo

import com.raudonikis.data.sharedpreferences.UserPreferences
import com.raudonikis.data_domain.mappers.GameModelMapper
import com.raudonikis.data_domain.models.GameModel
import com.raudonikis.network.igdb.IgdbApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class IgdbRepository @Inject constructor(
    private val igdbApi: IgdbApi,
    private val userPreferences: UserPreferences,
) {

    suspend fun updateAccessToken() {
        igdbApi.authorize().also {
            userPreferences.accessToken = it.accessToken
        }
    }

    suspend fun getGames(): List<GameModel> {
        return withContext(Dispatchers.IO) {
            GameModelMapper.fromGameResponseList(igdbApi.getGames())
        }
    }
}