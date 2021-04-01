package com.raudonikis.activity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.raudonikis.activity.databinding.FragmentActivityBinding
import com.raudonikis.activity.followers.UserFollowEvent
import com.raudonikis.activity.user_activity_item.UserActivityItem
import com.raudonikis.activity.user_activity_item.UserActivityItemMapper
import com.raudonikis.activity.user_item.UserItem
import com.raudonikis.activity.user_item.UserItemMapper
import com.raudonikis.common.Outcome
import com.raudonikis.common.extensions.showIf
import com.raudonikis.common_ui.extensions.*
import com.raudonikis.common_ui.item_decorations.VerticalPaddingItemDecoration
import com.raudonikis.data_domain.activity.models.UserActivity
import com.raudonikis.data_domain.user.User
import com.wada811.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class UserActivityFragment : Fragment(R.layout.fragment_activity) {

    private val viewModel: UserActivityViewModel by viewModels()
    private val binding: FragmentActivityBinding by viewBinding()

    /**
     * News feed
     */
    private val newsFeedItemAdapter = ItemAdapter<UserActivityItem>()
    private val newsFeedAdapter = FastAdapter.with(newsFeedItemAdapter)

    /**
     * User search
     */
    private val userSearchItemAdapter = ItemAdapter<UserItem>()
    private val userSearchAdapter = FastAdapter.with(userSearchItemAdapter)

    /**
     * Lifecycle hooks
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpNewsFeed()
        setUpUserSearch()
        setUpObservers()
    }

    /**
     * Set up
     */
    private fun setUpObservers() {
        viewModel.newsFeedState
            .onEach { updateNewsFeedState(it) }
            .observeInLifecycle(viewLifecycleOwner)
        viewModel.userSearchState
            .onEach { updateUserSearchState(it) }
            .observeInLifecycle(viewLifecycleOwner)
        viewModel.userActivityState
            .onEach { updateUserActivityState(it) }
            .observeInLifecycle(viewLifecycleOwner)
        viewModel.userFollowEvent
            .onEach { onUserFollowEvent(it) }
            .observeInLifecycle(viewLifecycleOwner)
        binding.layoutHeader.getSearchComponent().asFlow()
            .debounce(1000)
            .onEach {
                hideKeyboard()
                viewModel.searchUsers(it)
            }
            .observeInLifecycle(viewLifecycleOwner)
    }

    /**
     * News feed
     */
    private fun setUpNewsFeed() {
        binding.apply {
            recyclerNewsFeed.adapter = newsFeedAdapter
            recyclerNewsFeed.addItemDecoration(
                VerticalPaddingItemDecoration(
                    requireContext(),
                    R.dimen.spacing_small
                )
            )
        }
    }

    private fun updateNewsFeedState(state: Outcome<List<UserActivity>>) {
        state
            .onSuccess { newsFeed ->
                val newsFeedItems = newsFeed.sortedByDescending { it.createdAt }
                newsFeedItemAdapter.update(UserActivityItemMapper.fromUserActivityList(newsFeedItems))
            }
            .onFailure {
                showShortSnackbar("Failure")
            }
            .onLoading {
                showShortSnackbar("Loading...")
            }
            .onEmpty {
                showShortSnackbar("Empty")
            }
    }

    /**
     * User search
     */
    private fun setUpUserSearch() {
        binding.apply {
            recyclerUserSearch.adapter = userSearchAdapter
            recyclerUserSearch.addItemDecoration(
                VerticalPaddingItemDecoration(
                    requireContext(),
                    R.dimen.spacing_small
                )
            )
            layoutHeader.getSearchComponent().apply {
                setSearchQuery(viewModel.userSearchQuery)
                setOnClearClick {
                    viewModel.onSearchCleared()
                }
            }
            userSearchAdapter.onViewClick(R.id.button_follow) {
                viewModel.onFollowButtonClicked(it.user)
            }
        }
    }

    private fun updateUserSearchState(state: Outcome<List<User>>) {
        state
            .onSuccess {
                userSearchItemAdapter.update(UserItemMapper.fromUserList(it))
            }
            .onFailure {
                showShortSnackbar("User search failure")
            }
            .onEmpty {
                userSearchItemAdapter.clear()
            }
    }

    /**
     * User activity
     */
    private fun updateUserActivityState(state: UserActivityState) {
        with(binding) {
            groupUserSearch.showIf { state == UserActivityState.USER_SEARCH }
            groupNewsFeed.showIf { state == UserActivityState.NEWS_FEED }
        }
    }

    /**
     * Followers
     */
    private fun onUserFollowEvent(event: UserFollowEvent) {
        when (event) {
            UserFollowEvent.FAILURE -> {
                showShortSnackbar("Follow failure")
            }
            UserFollowEvent.SUCCESS -> {
                showShortSnackbar("Follow success")
            }
        }
    }
}