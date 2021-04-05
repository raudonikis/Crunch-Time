package com.raudonikis.data_domain.user.cache

import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.user.User
import kotlinx.coroutines.flow.Flow

interface UserDao {

    /**
     * User search
     */
    fun getUserSearchResults(): Flow<Outcome<List<User>>>
    fun setUserSearchResultsOutcome(outcome: Outcome<List<User>>)
    suspend fun updateUserSearchResults(userSearchResults: List<User>)

    /**
     * Followers
     */
    fun getFollowingUsers(): Flow<Outcome<List<User>>>
    fun setFollowingUsersOutcome(outcome: Outcome<List<User>>)
    fun addNewFollowingUser(user: User)
    suspend fun updateFollowingUsers(followingUsers: List<User>)
}