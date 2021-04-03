package com.raudonikis.data_domain.auth

import com.raudonikis.common.Outcome
import com.raudonikis.data.auth.AuthPreferences
import com.raudonikis.data_domain.user.UserPreferences
import com.raudonikis.data_domain.user.mappers.UserMapper
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
    private val authPreferences: AuthPreferences,
) {

    suspend fun logout(): Outcome<List<Nothing>> {
        return withContext(Dispatchers.IO) {
            safeNetworkResponse {
                gamesApi.logout()
            }
        }.toOutcome().onSuccess {
            clearUserData()
        }
    }

    suspend fun login(email: String, password: String): Outcome<LoginResponse> {
        return withContext(Dispatchers.IO) {
            val loginBody = LoginRequestBody(email, password)
            safeNetworkResponse {
                gamesApi.login(loginBody)
            }.toOutcome()
                .onSuccess { loginResponse ->
                    authPreferences.accessToken = loginResponse.accessToken
                    userPreferences.currentUser =
                        UserMapper.fromUserResponse(loginResponse.userResponse)
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
                    authPreferences.accessToken = response.accessToken
                    userPreferences.currentUser = UserMapper.fromUserResponse(response.user)
                }
        }
    }

    private fun clearUserData() {
        authPreferences.accessToken = ""
    }
}