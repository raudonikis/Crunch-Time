package com.raudonikis.data_domain.activity.repo

import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.activity.cache.daos.UserActivityDao
import com.raudonikis.data_domain.activity.mappers.UserActivityMapper
import com.raudonikis.data_domain.activity.models.UserActivity
import com.raudonikis.network.GamesApi
import com.raudonikis.network.utils.safeNetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserActivityRepository @Inject constructor(
    private val gamesApi: GamesApi,
    private val userActivityDao: UserActivityDao,
) {

    /**
     * Observables
     */
    fun getMyUserActivity(): Flow<Outcome<List<UserActivity>>> = userActivityDao.getMyUserActivity()
    fun getNewsFeed(): Flow<Outcome<List<UserActivity>>> = userActivityDao.getNewsFeed()

    /**
     * My activity
     */
    suspend fun updateMyUserActivity(): Outcome<List<UserActivity>> {
        withContext(Dispatchers.IO) {
            safeNetworkResponse {
                gamesApi.getUserActivity()
                    .map {
                        UserActivityMapper.fromUserActivityResponseList(it)
                    }
            }
        }.toOutcome().also { outcome ->
            userActivityDao.setMyUserActivityOutcome(outcome)
            return outcome
        }
    }

    /**
     * News feed
     */
    suspend fun updateNewsFeed(): Outcome<List<UserActivity>> {
        withContext(Dispatchers.IO) {
            safeNetworkResponse {
                gamesApi.getNewsFeed()
                    .map {
                        UserActivityMapper.fromUserActivityResponseList(it)
                    }
            }
        }.toOutcome().also { outcome ->
            userActivityDao.setNewsFeedOutcome(outcome)
            return outcome
        }
    }
}