package com.raudonikis.data_domain.user_follow

import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.user.User
import com.raudonikis.data_domain.user.cache.UserDao
import com.raudonikis.network.GamesApi
import com.raudonikis.network.utils.safeNetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserFollowUseCase @Inject constructor(
    private val gamesApi: GamesApi,
    private val userDao: UserDao
) {

    suspend fun followUser(user: User): Outcome<List<Nothing>> {
        return withContext(Dispatchers.IO) {
            safeNetworkResponse {
                gamesApi.followUser(user.uuid)
            }
        }.toOutcome().onSuccess {
            userDao.addNewFollowingUser(user)
        }
    }

    suspend fun unfollowUser(user: User): Outcome<List<Nothing>> {
        return withContext(Dispatchers.IO) {
            safeNetworkResponse {
                gamesApi.unfollowUser(user.uuid)
            }
        }.toOutcome().onSuccess {
            userDao.removeFollowingUser(user)
        }
    }
}