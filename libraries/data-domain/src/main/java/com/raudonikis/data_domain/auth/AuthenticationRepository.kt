package com.raudonikis.data_domain.auth

import com.raudonikis.data.user.UserPreferences
import com.raudonikis.network.GamesApi
import com.raudonikis.network.auth.LoginResponse
import com.raudonikis.network.utils.NetworkResponse
import com.raudonikis.network.utils.safeNetworkResponse
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
            val networkResponse = safeNetworkResponse { gamesApi.login(email, password) }
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
}