package com.raudonikis.activity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.raudonikis.activity.databinding.FragmentActivityBinding
import com.raudonikis.common_ui.follow.UserFollowEvent
import com.raudonikis.common_ui.follow.UserUnfollowEvent
import com.raudonikis.activity.news_feed.NewsFeedState
import com.raudonikis.activity.user_search.UserSearchState
import com.raudonikis.common_ui.user_activity_item.UserActivityItem
import com.raudonikis.common_ui.user_activity_item.UserActivityItemMapper
import com.raudonikis.common_ui.user_item.UserItem
import com.raudonikis.common_ui.user_item.UserItemMapper
import com.raudonikis.common.Outcome
import com.raudonikis.common.extensions.showIf
import com.raudonikis.common_ui.extensions.*
import com.raudonikis.common_ui.item_decorations.VerticalPaddingItemDecoration
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
        viewModel.userFollowEvent
            .onEach { onUserFollowEvent(it) }
            .observeInLifecycle(viewLifecycleOwner)
        viewModel.userUnfollowEvent
            .onEach { onUserUnfollowEvent(it) }
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
            swipeRefreshNewsFeed.setOnRefreshListener {
                viewModel.onNewsFeedRefresh()
            }
        }
    }

    private fun updateNewsFeedState(state: NewsFeedState) {
        with(binding) {
            groupNewsFeed.showIf { state is NewsFeedState.NewsFeed }
            if (state is NewsFeedState.NewsFeed) {
                groupNewsFeedEmpty.showIf { state.newsFeedOutcome is Outcome.Empty }
                swipeRefreshNewsFeed.isRefreshing = (state.newsFeedOutcome is Outcome.Loading)
                state.newsFeedOutcome
                    .onSuccess { newsFeed ->
                        newsFeedItemAdapter.update(
                            UserActivityItemMapper.fromUserActivityList(newsFeed)
                        )
                    }
                    .onFailure { errorMessage ->
                        if (errorMessage != null) {
                            showLongSnackbar(errorMessage)
                        } else {
                            showLongSnackbar(R.string.error_news_feed_generic)
                        }
                    }

            }
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
            userSearchAdapter.onViewClick(R.id.button_unfollow) {
                viewModel.onUnfollowButtonClicked(it.user)
            }
        }
    }

    private fun updateUserSearchState(state: UserSearchState) {
        with(binding) {
            groupUserSearch.showIf { state is UserSearchState.UserSearch }
            if (state is UserSearchState.UserSearch) {
                groupSearchEmpty.showIf { state.searchOutcome is Outcome.Empty }
                groupLoading.showIf { state.searchOutcome is Outcome.Loading }
                state.searchOutcome
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
        }
    }

    /**
     * Followers
     */
    private fun onUserFollowEvent(event: UserFollowEvent) {
        when (event) {
            //todo maybe disable follow functionality when loading
            UserFollowEvent.FAILURE -> {
                showShortSnackbar("Follow failure")
            }
            UserFollowEvent.SUCCESS -> {
                showShortSnackbar("Follow success")
            }
            else -> {
            }
        }
    }

    private fun onUserUnfollowEvent(event: UserUnfollowEvent) {
        when (event) {
            //todo maybe disable follow functionality when loading
            UserUnfollowEvent.FAILURE -> {
                showShortSnackbar("Unfollow failure")
            }
            UserUnfollowEvent.SUCCESS -> {
                showShortSnackbar("Unfollow success")
            }
            else -> {
            }
        }
    }
}