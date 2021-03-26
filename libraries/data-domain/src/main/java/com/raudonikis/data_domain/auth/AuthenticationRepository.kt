package com.raudonikis.data_domain.auth

import com.raudonikis.common.extensions.Outcome
import com.raudonikis.data.user.UserPreferences
import com.raudonikis.network.GamesApi
import com.raudonikis.network.auth.login.LoginRequestBody
import com.raudonikis.network.auth.login.LoginResponse
import com.raudonikis.network.auth.register.RegisterRequestBody
import com.raudonikis.network.auth.register.RegisterResponse
import com.raudonikis.network.utils.safeNetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    private val gamesApi: GamesApi,
    private val userPreferences: UserPreferences,
) {

    suspend fun login(email: String, password: String): Outcome<LoginResponse> {
        return withContext(Dispatchers.IO) {
            val loginBody = LoginRequestBody(email, password)
            safeNetworkResponse {
                gamesApi.login(loginBody)
            }.toOutcome()
                .onSuccess { loginResponse ->
                    userPreferences.apply {
                        accessToken = loginResponse.accessToken
                        userEmail = email
                    }
                }.onFailure {
                    Timber.e("Unable to login -> onFailure")
                }
        }
    }

    suspend fun register(
        email: String,
        password: String,
        passwordConfirmation: String,
        name: String
    ): Outcome<RegisterResponse> {
        return withContext(Dispatchers.IO) {
            val registerBody = RegisterRequestBody(email, password, passwordConfirmation, name)
            safeNetworkResponse {
                gamesApi.register(registerBody)
            }.toOutcome()
                .onSuccess { response ->
                    userPreferences.apply {
                        accessToken = response.accessToken
                        userEmail = response.user.email
                    }
                }
        }
    }
}