package com.raudonikis.data_domain.news_feed

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

class NewsFeedUseCase @Inject constructor(
    private val userActivityDao: UserActivityDao,
    private val gamesApi: GamesApi,
) {

    fun getNewsFeed(): Flow<Outcome<List<UserActivity>>> = userActivityDao.getNewsFeed()

    suspend fun updateNewsFeed(): Outcome<List<UserActivity>> {
        userActivityDao.setNewsFeedOutcome(Outcome.loading())
        withContext(Dispatchers.IO) {
            safeNetworkResponse {
                gamesApi.getNewsFeed()
                    .map {
                        UserActivityMapper.fromUserActivityResponseList(it)
                    }
            }
        }.toOutcome().also { outcome ->
            outcome.map { userActivities ->
                if (userActivities.isEmpty()) {
                    userActivityDao.setNewsFeedOutcome(Outcome.empty())
                    return outcome
                }
            }
            userActivityDao.setNewsFeedOutcome(outcome)
            return outcome
        }
    }
}