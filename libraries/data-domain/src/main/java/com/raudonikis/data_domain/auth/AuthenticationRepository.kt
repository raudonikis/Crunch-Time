package com.raudonikis.data_domain.auth

import com.raudonikis.data.UserPreferences
import com.raudonikis.network.GamesApi
import com.raudonikis.network.auth.LoginResponse
import com.raudonikis.network.utils.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    private val gamesApi: GamesApi,
    private val userPreferences: UserPreferences,
) {

    suspend fun login(email: String, password: String): NetworkResponse<LoginResponse> {
        return withContext(Dispatchers.IO) {
            val networkResponse = gamesApi.login(email, password)
            Timber.v("Logged in -> $networkResponse")
            networkResponse.onSuccess { loginResponse ->
                userPreferences.apply {
                    accessToken = loginResponse.accessToken
                    userEmail = email
                }
            }.onFailure {
                Timber.e("Unable to login -> onFailure")
            }
            return@withContext networkResponse
        }
    }

    suspend fun updateAccessToken() {
        /*withContext(Dispatchers.IO) {
            gamesApi.authorize().also { response ->
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
        }*/
    }

    /*suspend fun getGames(): List<GameModel> {
        return withContext(Dispatchers.IO) {
            gamesApi.getGames()
                .onSuccess {
                    GameModelMapper.fromGameResponseList(it)
                }
            emptyList() //todo handle errors
        }
    }*/
}