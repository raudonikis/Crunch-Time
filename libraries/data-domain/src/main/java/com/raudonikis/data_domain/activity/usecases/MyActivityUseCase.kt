package com.raudonikis.data_domain.activity.usecases

import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.activity.cache.UserActivityDao
import com.raudonikis.data_domain.activity.mappers.UserActivityMapper
import com.raudonikis.data_domain.activity.models.UserActivity
import com.raudonikis.network.GamesApi
import com.raudonikis.network.utils.safeNetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MyActivityUseCase @Inject constructor(
    private val userActivityDao: UserActivityDao,
    private val gamesApi: GamesApi,
) {
    fun getMyUserActivity(): Flow<Outcome<List<UserActivity>>> = userActivityDao.getMyUserActivity()

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
}