package com.raudonikis.data_domain.user_unfollow

import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.user.User
import com.raudonikis.network.GamesApi
import com.raudonikis.network.utils.safeNetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserUnfollowUseCase @Inject constructor(private val gamesApi: GamesApi) {

    suspend fun unfollowUser(user: User): Outcome<List<Nothing>> {
        return withContext(Dispatchers.IO) {
            safeNetworkResponse {
                gamesApi.unfollowUser(user.uuid)
            }
        }.toOutcome()
    }
}