package com.raudonikis.data_domain.activity.repo

import com.raudonikis.common.extensions.Outcome
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
    fun getUserActivity(): Flow<Outcome<List<UserActivity>>> = userActivityDao.getUserActivity()

    suspend fun updateUserActivity(): Outcome<List<UserActivity>> {
        withContext(Dispatchers.IO) {
            safeNetworkResponse {
                gamesApi.getUserActivity()
                    .map {
                        UserActivityMapper.fromUserActivityResponseList(it)
                    }
            }
        }.toOutcome().also { outcome ->
            userActivityDao.setUserActivityOutcome(outcome)
            return outcome
        }
    }
}