package com.raudonikis.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.raudonikis.common.Outcome
import com.raudonikis.common_ui.extensions.observeInLifecycle
import com.raudonikis.common_ui.extensions.onClick
import com.raudonikis.common_ui.game_cover_item.GameCoverItem
import com.raudonikis.data_domain.user.User
import com.raudonikis.profile.databinding.FragmentProfileBinding
import com.wada811.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by hiltNavGraphViewModels(R.id.navigation_profile)
    private val binding: FragmentProfileBinding by viewBinding()

    /**
     * Game collection
     */
    private val gameCollectionItemAdapter = ItemAdapter<GameCoverItem>()
    private val gameCollectionAdapter = FastAdapter.with(gameCollectionItemAdapter)

    /**
     * Lifecycle hooks
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
        setUpObservers()
    }

    /**
     * Listeners
     */
    private fun setUpListeners() {
        gameCollectionAdapter.onClick {
            viewModel.onGameClicked(it.game)
        }
        with(binding) {
            buttonFollowing.setOnClickListener {
                viewModel.onFollowingClicked()
            }
            buttonFollowers.setOnClickListener {
                viewModel.onFollowersClicked()
            }
            /*cardPlayed.setOnClickListener {
                viewModel.onCollectionClicked(GameStatus.PLAYED)
            }
            cardPlaying.setOnClickListener {
                viewModel.onCollectionClicked(GameStatus.PLAYING)
            }
            cardWant.setOnClickListener {
                viewModel.onCollectionClicked(GameStatus.WANT)
            }*/
        }
    }

    /**
     * Observers
     */
    private fun setUpObservers() {
        viewModel.activitiesState
            .onEach { }
            .observeInLifecycle(viewLifecycleOwner)
        viewModel.followingUsersState
            .onEach { onFollowingUsersState(it) }
            .observeInLifecycle(viewLifecycleOwner)
        viewModel.userState
            .onEach { onUserState(it) }
            .observeInLifecycle(viewLifecycleOwner)
    }

    /**
     * User
     */
    private fun onUserState(state: Outcome<User>) {
        with(binding) {
            state
                .onSuccess { user ->
                    textUserEmail.text = user.email
                    textUserName.text = user.name
                }
        }
    }

    /**
     * Followers
     */
    private fun onFollowingUsersState(state: Outcome<List<User>>) {
        state
            .onSuccess { followingUsers ->
                binding.buttonFollowing.text =
                    getString(R.string.button_following, followingUsers.size)
            }
    }
}