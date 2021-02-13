package com.raudonikis.data_domain.repo

import com.raudonikis.data.sharedpreferences.UserPreferences
import com.raudonikis.data_domain.mappers.GameModelMapper
import com.raudonikis.data_domain.models.GameModel
import com.raudonikis.network.extensions.onNetworkError
import com.raudonikis.network.extensions.onServerError
import com.raudonikis.network.extensions.onSuccess
import com.raudonikis.network.extensions.onUnknownError
import com.raudonikis.network.igdb.IgdbApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class IgdbRepository @Inject constructor(
    private val igdbApi: IgdbApi,
    private val userPreferences: UserPreferences,
) {

    suspend fun updateAccessToken() {
        withContext(Dispatchers.IO) {
            igdbApi.authorize().also { response ->
                response
                    .onSuccess {
                        userPreferences.accessToken = it.accessToken
                    }
                    .onNetworkError {

                    }
                    .onServerError {

                    }
                    .onUnknownError {

                    }
            }
        }
    }

    suspend fun getGames(): List<GameModel> {
        return withContext(Dispatchers.IO) {
            igdbApi.getGames()
                .onSuccess {
                    GameModelMapper.fromGameResponseList(it)
                }
            emptyList() //todo handle errors
        }
    }
}