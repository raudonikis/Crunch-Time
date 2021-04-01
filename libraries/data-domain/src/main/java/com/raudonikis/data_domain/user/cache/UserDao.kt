package com.raudonikis.data_domain.user.cache

import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.user.User
import kotlinx.coroutines.flow.Flow

interface UserDao {

    /**
     * Search
     */
    fun getUserSearchResults(): Flow<Outcome<List<User>>>
    fun setUserSearchResultsOutcome(outcome: Outcome<List<User>>)
    suspend fun updateUserSearchResults(userSearchResults: List<User>)
}