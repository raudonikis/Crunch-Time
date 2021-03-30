package com.raudonikis.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raudonikis.data_domain.activity.repo.UserActivityRepository
import com.raudonikis.data_domain.user.repo.UserRepository
import com.raudonikis.navigation.NavigationDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserActivityViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
    private val userActivityRepository: UserActivityRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    /**
     * Observables
     */
    val userSearchResultsState = userRepository.getUserSearchResults()
    val newsFeedState = userActivityRepository.getNewsFeed()

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
    private fun searchUsers(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.searchUsers(name)
        }
    }
}