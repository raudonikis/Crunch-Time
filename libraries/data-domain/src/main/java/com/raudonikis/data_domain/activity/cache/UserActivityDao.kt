package com.raudonikis.data_domain.activity.cache

import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.activity.models.UserActivity
import com.raudonikis.data_domain.game_status.GameStatus
import kotlinx.coroutines.flow.Flow

interface UserActivityDao {

    /**
     * My user activity
     */
    fun getMyUserActivity(): Flow<Outcome<List<UserActivity>>>
    fun setMyUserActivityOutcome(outcome: Outcome<List<UserActivity>>)
    fun addNewMyActivity(userActivity: UserActivity)
    suspend fun updateMyUserActivity(userActivity: List<UserActivity>)

    /**
     * News feed
     */
    fun getNewsFeed(): Flow<Outcome<List<UserActivity>>>
    fun setNewsFeedOutcome(outcome: Outcome<List<UserActivity>>)
    suspend fun updateNewsFeed(newsFeed: List<UserActivity>)
}