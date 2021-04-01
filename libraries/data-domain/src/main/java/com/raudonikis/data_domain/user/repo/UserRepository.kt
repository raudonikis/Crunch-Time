package com.raudonikis.data_domain.user.repo

import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.user.User
import com.raudonikis.data_domain.user.cache.UserDao
import com.raudonikis.data_domain.user.mappers.UserMapper
import com.raudonikis.network.GamesApi
import com.raudonikis.network.utils.safeNetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val gamesApi: GamesApi,
    private val userDao: UserDao,
) {

    /**
     * Observables
     */
    fun getUserSearchResults(): Flow<Outcome<List<User>>> = userDao.getUserSearchResults()
    fun getFollowingUsers(): Flow<Outcome<List<User>>> = userDao.getFollowingUsers()

    /**
     * User search
     */
    suspend fun searchUsers(name: String): Outcome<List<User>> {
        withContext(Dispatchers.IO) {
            safeNetworkResponse {
                gamesApi.searchUsers(name)
                    .map {
                        UserMapper.fromUserSearchResponseList(it)
                    }
            }
        }.toOutcome().also { outcome ->
            userDao.setUserSearchResultsOutcome(outcome)
            return outcome
        }
    }

    fun clearUserSearchResults() {
        userDao.setUserSearchResultsOutcome(Outcome.empty())
    }

    /**
     * Followers
     */
    suspend fun updateFollowingUsers(): Outcome<List<User>> {
        withContext(Dispatchers.IO) {
            safeNetworkResponse {
                gamesApi.getFollowingUsers()
                    .map { UserMapper.fromUserResponseList(it) }
            }
        }.toOutcome().also { outcome ->
            userDao.setFollowingUsersOutcome(outcome)
            return outcome
        }
    }

    suspend fun followUser(user: User): Outcome<List<Nothing>> {
        return withContext(Dispatchers.IO) {
            safeNetworkResponse {
                gamesApi.followUser(user.uuid)
            }
        }.toOutcome()
    }

    suspend fun unfollowUser(user: User): Outcome<List<Nothing>> {
        return withContext(Dispatchers.IO) {
            safeNetworkResponse {
                gamesApi.unfollowUser(user.uuid)
            }
        }.toOutcome()
    }
}