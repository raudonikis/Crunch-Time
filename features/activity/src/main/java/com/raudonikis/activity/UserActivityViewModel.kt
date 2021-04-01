package com.raudonikis.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raudonikis.data_domain.activity.repo.UserActivityRepository
import com.raudonikis.data_domain.user.repo.UserRepository
import com.raudonikis.navigation.NavigationDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserActivityViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
    private val userActivityRepository: UserActivityRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _userActivityState: MutableStateFlow<UserActivityState> =
        MutableStateFlow(UserActivityState.NEWS_FEED)

    /**
     * User search
     */
    private var userSearchJob: Job? = null
    var userSearchQuery = ""
        private set

    /**
     * Observables
     */
    val userSearchState = userRepository.getUserSearchResults()
    val newsFeedState = userActivityRepository.getNewsFeed()
    val userActivityState: Flow<UserActivityState> = _userActivityState

    init {
        updateNewsFeed()
    }

    /**
     * News feed
     */
    private fun updateNewsFeed() {
        viewModelScope.launch(Dispatchers.IO) {
            userActivityRepository.updateNewsFeed()
        }
    }

    /**
     * Users
     */
    fun searchUsers(name: String) {
        userSearchJob?.cancel()
        userSearchQuery = name
        if (name.isBlank()) {
            onSearchCleared()
            return
        }
        _userActivityState.value = UserActivityState.USER_SEARCH
        userSearchJob = viewModelScope.launch(Dispatchers.IO) {
            userRepository.searchUsers(name)
        }
    }

    /**
     * Events
     */
    fun onSearchCleared() {
        userSearchJob?.cancel()
        userSearchJob = null
        userRepository.clearUserSearchResults()
        _userActivityState.value = UserActivityState.NEWS_FEED
    }
}