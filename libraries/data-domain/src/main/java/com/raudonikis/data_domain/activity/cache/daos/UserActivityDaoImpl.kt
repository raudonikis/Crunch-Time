package com.raudonikis.data_domain.activity.cache.daos

import com.raudonikis.common.extensions.Outcome
import com.raudonikis.data_domain.activity.models.UserActivity
import com.raudonikis.data_domain.game_status.GameStatus
import com.raudonikis.data_domain.game_status.GameStatusUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Singleton

@Singleton
class UserActivityDaoImpl : UserActivityDao {

    /**
     * Data
     */
    private val userActivity: MutableStateFlow<Outcome<List<UserActivity>>> =
        MutableStateFlow(Outcome.empty())

    override fun getUserActivity(): Flow<Outcome<List<UserActivity>>> {
        return userActivity
    }

    override fun setUserActivityOutcome(outcome: Outcome<List<UserActivity>>) {
        userActivity.value = outcome
    }

    override suspend fun updateUserActivity(userActivities: List<UserActivity>) {
        userActivity.value = Outcome.success(userActivities)
    }

    override suspend fun updateGameStatus(id: Long, gameStatus: GameStatus) {
        userActivity.value =
            GameStatusUtils.updateGameStatusForActivities(userActivity.value, id, gameStatus)
    }
}