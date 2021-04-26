package com.raudonikis.activity.user_search

import com.raudonikis.common.Outcome
import com.raudonikis.data_domain.user.User

sealed class UserSearchState {
    object Disabled : UserSearchState()
    data class UserSearch(val searchOutcome: Outcome<List<User>>) : UserSearchState()
}
