package com.raudonikis.data_domain.user_follow

import com.raudonikis.common.Outcome
import com.raudonikis.core.providers.di.IODispatcher
import com.raudonikis.data_domain.user.User
import com.raudonikis.data_domain.user.cache.UserDao
import com.raudonikis.network.GamesApi
import com.raudonikis.network.utils.safeNetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserFollowUseCase @Inject constructor(
    private val gamesApi: GamesApi,
    private val userDao: UserDao,
    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher,
) {

    suspend fun followUser(user: User): Outcome<List<Nothing>> {
        return withContext(ioDispatcher) {
            safeNetworkResponse {
                gamesApi.followUser(user.uuid)
            }
        }.toOutcome().also { outcome ->
            if (outcome is Outcome.Success || outcome is Outcome.Empty) {
                userDao.addNewFollowingUser(user)
            }
        }
    }

    suspend fun unfollowUser(user: User): Outcome<List<Nothing>> {
        return withContext(ioDispatcher) {
            safeNetworkResponse {
                gamesApi.unfollowUser(user.uuid)
            }
        }.toOutcome().also { outcome ->
            if (outcome is Outcome.Success || outcome is Outcome.Empty) {
                userDao.removeFollowingUser(user)
            }
        }
    }
}