package com.raudonikis.data_domain.user.cache

import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.user.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDaoImpl @Inject constructor() : UserDao {

    /**
     * Data
     */
    private val userSearchResultsState: MutableStateFlow<Outcome<List<User>>> =
        MutableStateFlow(Outcome.empty())
    private val followingUsersState: MutableStateFlow<Outcome<List<User>>> =
        MutableStateFlow(Outcome.empty())

    /**
     * User search
     */
    override fun getUserSearchResults(): Flow<Outcome<List<User>>> = userSearchResultsState

    override fun setUserSearchResultsOutcome(outcome: Outcome<List<User>>) {
        userSearchResultsState.value = outcome
    }

    override suspend fun updateUserSearchResults(userSearchResults: List<User>) {
        userSearchResultsState.value = Outcome.success(userSearchResults)
    }

    private fun updateSearchResults(user: User) {
        userSearchResultsState.value = userSearchResultsState.value.map { users ->
            users.map {
                if (it.uuid == user.uuid) {
                    user
                } else {
                    it
                }
            }
        }
    }

    override fun getFollowingUsers(): Flow<Outcome<List<User>>> = followingUsersState

    override fun setFollowingUsersOutcome(outcome: Outcome<List<User>>) {
        followingUsersState.value = outcome
    }

    override fun addNewFollowingUser(user: User) {
        updateSearchResults(user.copy(isFollowed = true))
        followingUsersState.value.onSuccess {
            followingUsersState.value = followingUsersState.value.map { users ->
                users.plus(user.copy(isFollowed = true))
            }
            return
        }
        followingUsersState.value = Outcome.success(listOf(user))
    }

    override fun removeFollowingUser(user: User) {
        updateSearchResults(user.copy(isFollowed = false))
        followingUsersState.value = followingUsersState.value.map { users ->
            users.filter { it.uuid != user.uuid }
        }
    }

    override suspend fun updateFollowingUsers(followingUsers: List<User>) {
        followingUsersState.value = Outcome.success(followingUsers)
    }
}