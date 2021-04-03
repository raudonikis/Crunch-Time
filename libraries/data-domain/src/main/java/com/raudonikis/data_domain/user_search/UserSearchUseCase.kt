package com.raudonikis.data_domain.user_search

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

class UserSearchUseCase @Inject constructor(
    private val userDao: UserDao,
    private val gamesApi: GamesApi,
) {

    fun getUserSearchResults(): Flow<Outcome<List<User>>> = userDao.getUserSearchResults()

    suspend fun searchUsers(name: String): Outcome<List<User>> {
        userDao.setUserSearchResultsOutcome(Outcome.loading())
        withContext(Dispatchers.IO) {
            safeNetworkResponse {
                gamesApi.searchUsers(name)
                    .map {
                        UserMapper.fromUserSearchResponseList(it)
                    }
            }
        }.toOutcome().also { outcome ->
            outcome.map { users ->
                if (users.isEmpty()) {
                    userDao.setUserSearchResultsOutcome(Outcome.empty())
                    return outcome
                }
            }
            userDao.setUserSearchResultsOutcome(outcome)
            return outcome
        }
    }

    fun clearUserSearchResults() {
        userDao.setUserSearchResultsOutcome(Outcome.empty())
    }
}