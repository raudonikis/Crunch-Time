package com.raudonikis.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raudonikis.common_ui.follow.UserFollowEvent
import com.raudonikis.common_ui.follow.UserUnfollowEvent
import com.raudonikis.activity.news_feed.NewsFeedState
import com.raudonikis.activity.user_search.UserSearchState
import com.raudonikis.data_domain.news_feed.NewsFeedUseCase
import com.raudonikis.data_domain.user.User
import com.raudonikis.data_domain.user_follow.UserFollowUseCase
import com.raudonikis.data_domain.user_search.UserSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
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
    private val _userUnfollowEvent: MutableSharedFlow<UserUnfollowEvent> = MutableSharedFlow()

    /**
     * User search
     */
    private var userSearchJob: Job? = null
    var userSearchQuery = ""
        private set

    /**
     * Observables
     */
    val userSearchState: StateFlow<UserSearchState> = userSearchUseCase.getUserSearchResults()
        .combine(_userActivityState) { userSearchState, userActivityState ->
            when (userActivityState) {
                UserActivityState.USER_SEARCH -> {
                    UserSearchState.UserSearch(userSearchState)
                }
                else -> UserSearchState.Disabled
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, UserSearchState.Disabled)
    val newsFeedState: StateFlow<NewsFeedState> =
        newsFeedUseCase.getNewsFeed().combine(_userActivityState) { newsFeed, userActivityState ->
            when (userActivityState) {
                UserActivityState.NEWS_FEED -> {
                    NewsFeedState.NewsFeed(newsFeed)
                }
                else -> NewsFeedState.Disabled
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, NewsFeedState.Disabled)
    val userFollowEvent: Flow<UserFollowEvent> = _userFollowEvent
    val userUnfollowEvent: Flow<UserUnfollowEvent> = _userUnfollowEvent

    init {
        updateNewsFeed()
    }

    /**
     * News feed
     */
    private fun updateNewsFeed() {
        viewModelScope.launch {
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
        userSearchJob = viewModelScope.launch {
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
        viewModelScope.launch {
            newsFeedUseCase.updateNewsFeed()
        }
    }

    fun onFollowButtonClicked(user: User) {
        viewModelScope.launch {
            _userFollowEvent.emit(UserFollowEvent.LOADING)
            userFollowUseCase.followUser(user)
                .onSuccessOrEmpty { _userFollowEvent.emit(UserFollowEvent.SUCCESS) }
                .onFailure { _userFollowEvent.emit(UserFollowEvent.FAILURE) }
        }
    }

    fun onUnfollowButtonClicked(user: User) {
        viewModelScope.launch {
            _userUnfollowEvent.emit(UserUnfollowEvent.LOADING)
            userFollowUseCase.unfollowUser(user)
                .onSuccessOrEmpty { _userUnfollowEvent.emit(UserUnfollowEvent.SUCCESS) }
                .onFailure { _userUnfollowEvent.emit(UserUnfollowEvent.FAILURE) }
        }
    }
}