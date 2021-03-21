package com.raudonikis.data_domain.activity.cache.daos

import com.raudonikis.common.extensions.Outcome
import com.raudonikis.data_domain.activity.models.UserActivity
import com.raudonikis.data_domain.game_status.GameStatus
import kotlinx.coroutines.flow.Flow

interface UserActivityDao {

    /**
     * User activity
     */
    fun getUserActivity(): Flow<Outcome<List<UserActivity>>>
    fun setUserActivityOutcome(outcome: Outcome<List<UserActivity>>)
    suspend fun updateUserActivity(userActivities: List<UserActivity>)

    /**
     * Game status
     */
    suspend fun updateGameStatus(id: Long, gameStatus: GameStatus)
}