package com.raudonikis.data_domain.activity.cache.daos

import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.activity.models.UserActivity
import com.raudonikis.data_domain.game_status.GameStatus
import com.raudonikis.data_domain.game_status.GameStatusUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserActivityDaoImpl @Inject constructor() : UserActivityDao {

    /**
     * Data
     */
    private val userActivity: MutableStateFlow<Outcome<List<UserActivity>>> =
        MutableStateFlow(Outcome.empty())
    private val newsFeed: MutableStateFlow<Outcome<List<UserActivity>>> =
        MutableStateFlow(Outcome.empty())

    /**
     * My user activity
     */
    override fun getMyUserActivity(): Flow<Outcome<List<UserActivity>>> {
        return userActivity
    }

    override fun setMyUserActivityOutcome(outcome: Outcome<List<UserActivity>>) {
        userActivity.value = outcome
    }

    override suspend fun updateMyUserActivity(userActivity: List<UserActivity>) {
        this.userActivity.value = Outcome.success(userActivity)
    }

    /**
     * News feed
     */
    override fun getNewsFeed(): Flow<Outcome<List<UserActivity>>> {
        return newsFeed
    }

    override fun setNewsFeedOutcome(outcome: Outcome<List<UserActivity>>) {
        newsFeed.value = outcome
    }

    override suspend fun updateNewsFeed(newsFeed: List<UserActivity>) {
        this.newsFeed.value = Outcome.success(newsFeed)
    }

    /**
     * Game status
     */
    override suspend fun updateGameStatus(id: Long, gameStatus: GameStatus) {
        userActivity.value =
            GameStatusUtils.updateGameStatusForActivities(userActivity.value, id, gameStatus)
    }
}