package com.raudonikis.data_domain.activity.cache

import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.activity.models.UserActivity
import com.raudonikis.data_domain.game_status.GameStatus
import com.raudonikis.data_domain.game_status.GameStatusUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserActivityDaoImpl @Inject constructor() : UserActivityDao {

    /**
     * Data
     */
    private val myUserActivity: MutableStateFlow<Outcome<List<UserActivity>>> =
        MutableStateFlow(Outcome.empty())
    private val newsFeed: MutableStateFlow<Outcome<List<UserActivity>>> =
        MutableStateFlow(Outcome.empty())

    /**
     * My user activity
     */
    override fun getMyUserActivity(): Flow<Outcome<List<UserActivity>>> {
        return myUserActivity.map { outcome ->
            outcome.map { userActivities ->
                userActivities.sortedByDescending { userActivity ->
                    userActivity.createdAt
                }
            }
        }
    }

    override fun setMyUserActivityOutcome(outcome: Outcome<List<UserActivity>>) {
        myUserActivity.value = outcome
    }

    override fun addNewMyActivity(userActivity: UserActivity) {
        myUserActivity.value = myUserActivity.value.map { userActivities ->
            userActivities.plus(userActivity)
        }
    }

    override suspend fun updateMyUserActivity(userActivity: List<UserActivity>) {
        this.myUserActivity.value = Outcome.success(userActivity)
    }

    /**
     * News feed
     */
    override fun getNewsFeed(): Flow<Outcome<List<UserActivity>>> {
        return newsFeed.map { outcome ->
            outcome.map { newsFeed ->
                newsFeed.sortedByDescending { userActivity ->
                    userActivity.createdAt
                }
            }
        }
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
        myUserActivity.value =
            GameStatusUtils.updateGameStatusForActivities(myUserActivity.value, id, gameStatus)
    }
}