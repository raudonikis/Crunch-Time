package com.raudonikis.profile.followers

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.navArgs
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.raudonikis.common.Outcome
import com.raudonikis.common_ui.extensions.observeInLifecycle
import com.raudonikis.common_ui.extensions.onViewClick
import com.raudonikis.common_ui.extensions.showShortSnackbar
import com.raudonikis.common_ui.extensions.update
import com.raudonikis.common_ui.follow.UserFollowEvent
import com.raudonikis.common_ui.follow.UserUnfollowEvent
import com.raudonikis.common_ui.item_decorations.VerticalPaddingItemDecoration
import com.raudonikis.common_ui.user_item.UserItem
import com.raudonikis.common_ui.user_item.UserItemMapper
import com.raudonikis.data_domain.user.User
import com.raudonikis.profile.ProfileViewModel
import com.raudonikis.profile.R
import com.raudonikis.profile.databinding.FragmentFollowersBinding
import com.wada811.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class FollowersFragment : Fragment(R.layout.fragment_followers) {

    private val viewModel: ProfileViewModel by hiltNavGraphViewModels(R.id.navigation_profile)
    private val binding: FragmentFollowersBinding by viewBinding()
    private val args: FollowersFragmentArgs by navArgs()

    /**
     * Followers/Following
     */
    private val followersItemAdapter = ItemAdapter<UserItem>()
    private val followersAdapter = FastAdapter.with(followersItemAdapter)

    /**
     * Lifecycle hooks
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        setUpFollowers()
        setUpObservers()
    }

    private fun setUpView() {
        when (args.followerType) {
            FollowerType.FOLLOWING -> binding.header.setTitle(getString(R.string.title_following))
            FollowerType.FOLLOWER -> binding.header.setTitle(getString(R.string.title_followers))
        }
    }

    private fun setUpObservers() {
        when (args.followerType) {
            FollowerType.FOLLOWING -> {
                viewModel.followingUsersState
                    .onEach { onFollowersState(it) }
                    .observeInLifecycle(viewLifecycleOwner)
            }
            FollowerType.FOLLOWER -> {
            }
        }
        viewModel.userFollowEvent
            .onEach { onUserFollowEvent(it) }
            .observeInLifecycle(viewLifecycleOwner)
        viewModel.userUnfollowEvent
            .onEach { onUserUnfollowEvent(it) }
            .observeInLifecycle(viewLifecycleOwner)
    }

    /**
     * Followers
     */
    private fun setUpFollowers() {
        with(binding) {
            recyclerFollowers.adapter = followersAdapter
            recyclerFollowers.addItemDecoration(
                VerticalPaddingItemDecoration(
                    requireContext(),
                    R.dimen.spacing_small
                )
            )
            followersAdapter.onViewClick(R.id.button_follow) {
                viewModel.onFollowButtonClicked(it.user)
            }
            followersAdapter.onViewClick(R.id.button_unfollow) {
                viewModel.onUnfollowButtonClicked(it.user)
            }
        }
    }

    private fun onFollowersState(state: Outcome<List<User>>) {
        state
            .onSuccess { followers ->
                followersItemAdapter.update(UserItemMapper.fromUserList(followers))
            }
    }

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