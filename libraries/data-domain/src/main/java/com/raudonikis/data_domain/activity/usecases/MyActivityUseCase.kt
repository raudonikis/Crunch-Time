package com.raudonikis.data_domain.activity.usecases

import com.raudonikis.common.Outcome
import com.raudonikis.core.providers.di.IODispatcher
import com.raudonikis.data_domain.activity.cache.UserActivityDao
import com.raudonikis.data_domain.activity.mappers.UserActivityMapper
import com.raudonikis.data_domain.activity.models.UserActivity
import com.raudonikis.network.GamesApi
import com.raudonikis.network.utils.safeNetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MyActivityUseCase @Inject constructor(
    private val userActivityDao: UserActivityDao,
    private val gamesApi: GamesApi,
    @IODispatcher
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    fun getMyUserActivity(): Flow<Outcome<List<UserActivity>>> = userActivityDao.getMyUserActivity()
        .map { outcome ->
            outcome.map { list ->
                list.subList(0, ACTIVITY_HISTORY_SIZE)
            }
        }

    suspend fun updateMyUserActivity(): Outcome<List<UserActivity>> {
        withContext(ioDispatcher) {
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

    companion object {
        private const val ACTIVITY_HISTORY_SIZE = 20

    }
}