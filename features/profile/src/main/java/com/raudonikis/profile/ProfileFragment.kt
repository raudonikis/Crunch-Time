package com.raudonikis.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.raudonikis.common.Outcome
import com.raudonikis.common_ui.extensions.observeInLifecycle
import com.raudonikis.common_ui.extensions.onClick
import com.raudonikis.common_ui.game_item.GameItem
import com.raudonikis.data_domain.user.User
import com.raudonikis.profile.databinding.FragmentProfileBinding
import com.wada811.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by viewModels()
    private val binding: FragmentProfileBinding by viewBinding()

    /**
     * Game collection
     */
    private val gameCollectionItemAdapter = ItemAdapter<GameItem>()
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
    }

    /**
     * Followers
     */
    private fun onFollowingUsersState(state: Outcome<List<User>>) {

    }
}