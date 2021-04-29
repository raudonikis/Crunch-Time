package com.raudonikis.data_domain.user_following

import com.raudonikis.common.Outcome
import com.raudonikis.core.providers.di.IODispatcher
import com.raudonikis.data_domain.user.User
import com.raudonikis.data_domain.user.cache.UserDao
import com.raudonikis.data_domain.user.mappers.UserMapper
import com.raudonikis.network.GamesApi
import com.raudonikis.network.utils.safeNetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserFollowingUseCase @Inject constructor(
    private val gamesApi: GamesApi,
    private val userDao: UserDao,
    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher,
) {

    /**
     * Observables
     */
    fun getFollowingUsers(): Flow<Outcome<List<User>>> = userDao.getFollowingUsers()

    /**
     * Followers
     */
    suspend fun updateFollowingUsers(): Outcome<List<User>> {
        userDao.setFollowingUsersOutcome(Outcome.loading())
        withContext(ioDispatcher) {
            safeNetworkResponse {
                gamesApi.getFollowingUsers()
                    .map { UserMapper.fromUserResponseList(it) }
            }
        }.toOutcome().also { outcome ->
            userDao.setFollowingUsersOutcome(outcome)
            return outcome
        }
    }
}