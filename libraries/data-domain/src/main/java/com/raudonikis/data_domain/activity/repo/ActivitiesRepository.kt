package com.raudonikis.data_domain.activity.repo

import com.raudonikis.data_domain.activity.models.UserActivity
import com.raudonikis.data_domain.activity.mappers.UserActivityMapper
import com.raudonikis.network.GamesApi
import com.raudonikis.network.utils.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ActivitiesRepository @Inject constructor(
    private val gamesApi: GamesApi,
) {

    suspend fun getUserActivities(): NetworkResponse<List<UserActivity>> {
        return withContext(Dispatchers.IO) {
            gamesApi.getUserActivities()
                .map {
                    UserActivityMapper.fromUserActivityResponseList(it)
                }
        }
    }
}