package com.raudonikis.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raudonikis.activity.followers.UserFollowEvent
import com.raudonikis.data_domain.news_feed.NewsFeedUseCase
import com.raudonikis.data_domain.user.User
import com.raudonikis.data_domain.user_follow.UserFollowUseCase
import com.raudonikis.data_domain.user_search.UserSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserActivityViewModel @Inject constructor(
    // Use cases
    private val newsFeedUseCase: NewsFeedUseCase,
    private val userSearchUseCase: UserSearchUseCase,
    private val userFollowUseCase: UserFollowUseCase,
) : ViewModel() {

    private val _userActivityState: MutableStateFlow<UserActivityState> =
        MutableStateFlow(UserActivityState.NEWS_FEED)
    private val _userFollowEvent: MutableSharedFlow<UserFollowEvent> = MutableSharedFlow()

    /**
     * User search
     */
    private var userSearchJob: Job? = null
    var userSearchQuery = ""
        private set

    /**
     * Observables
     */
    val userSearchState = userSearchUseCase.getUserSearchResults()
    val newsFeedState = newsFeedUseCase.getNewsFeed()
    val userActivityState: Flow<UserActivityState> = _userActivityState
    val userFollowEvent: Flow<UserFollowEvent> = _userFollowEvent

    init {
        updateNewsFeed()
    }

    /**
     * News feed
     */
    private fun updateNewsFeed() {
        viewModelScope.launch(Dispatchers.IO) {
            newsFeedUseCase.updateNewsFeed()
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
            userSearchUseCase.searchUsers(name)
        }
    }

    /**
     * Events
     */
    fun onSearchCleared() {
        userSearchJob?.cancel()
        userSearchJob = null
        userSearchUseCase.clearUserSearchResults()
        _userActivityState.value = UserActivityState.NEWS_FEED
    }

    fun onNewsFeedRefresh() {
        viewModelScope.launch(Dispatchers.IO) {
            newsFeedUseCase.updateNewsFeed()
        }
    }

    fun onFollowButtonClicked(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            _userFollowEvent.emit(UserFollowEvent.LOADING)
            userFollowUseCase.followUser(user)
                .onSuccess { _userFollowEvent.emit(UserFollowEvent.SUCCESS) }
                .onFailure { _userFollowEvent.emit(UserFollowEvent.FAILURE) }
        }
    }
}