package com.raudonikis.data_domain.auth

import com.raudonikis.data.UserPreferences
import com.raudonikis.network.GamesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    private val gamesApi: GamesApi,
    private val userPreferences: UserPreferences,
) {

    /**
     * todo: return wrapper for error handling
     * todo: for now return [true] if success, [false] if failed
     */
    suspend fun login(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val loginResponse = gamesApi.login(email, password)
                Timber.v("Logged in -> $loginResponse")
                userPreferences.accessToken = loginResponse.accessToken
                userPreferences.userEmail = email
                true
            } catch (e: Exception) {
                Timber.e("Unable to login -> ${e.message}")
                false
            }
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