package com.raudonikis.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.raudonikis.common.Outcome
import com.raudonikis.common_ui.extensions.*
import com.raudonikis.common_ui.game_cover_item.GameCoverItem
import com.raudonikis.common_ui.game_cover_item.GameCoverItemMapper
import com.raudonikis.common_ui.item_decorations.HorizontalPaddingItemDecoration
import com.raudonikis.common_ui.item_decorations.VerticalPaddingItemDecoration
import com.raudonikis.common_ui.user_activity_item.UserActivityItem
import com.raudonikis.common_ui.user_activity_item.UserActivityItemMapper
import com.raudonikis.data_domain.activity.models.UserActivity
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.user.User
import com.raudonikis.profile.collection.GameCollectionTab
import com.raudonikis.profile.collection.mappers.GameCollectionTypeMapper
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
     * My activity
     */
    private val myActivityItemAdapter = ItemAdapter<UserActivityItem>()
    private val myActivityAdapter = FastAdapter.with(myActivityItemAdapter)

    /**
     * Lifecycle hooks
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
        setUpGameCollections()
        setUpMyActivity()
        setUpObservers()
    }

    /**
     * Listeners
     */
    private fun setUpListeners() {
        gameCollectionAdapter.onClick { gameItem ->
            viewModel.onGameClicked(gameItem.game)
        }
        with(binding) {
            buttonFollowing.setOnClickListener {
                viewModel.onFollowingClicked()
            }
            tabLayoutCollections.onTabSelected { position ->
                val gameCollectionType = GameCollectionTypeMapper.fromGameCollectionTab(position)
                viewModel.onGameCollectionTabSwitched(gameCollectionType)
            }
        }
    }

    /**
     * Observers
     */
    private fun setUpObservers() {
        viewModel.myActivityState
            .onEach { onMyActivityState(it) }
            .observeInLifecycle(viewLifecycleOwner)
        viewModel.followingUsersState
            .onEach { onFollowingUsersState(it) }
            .observeInLifecycle(viewLifecycleOwner)
        viewModel.userState
            .onEach { onUserState(it) }
            .observeInLifecycle(viewLifecycleOwner)
        viewModel.gameCollection
            .onEach { onGameCollectionState(it) }
            .observeInLifecycle(viewLifecycleOwner)
    }

    private fun onMyActivityState(state: Outcome<List<UserActivity>>) {
        state
            .onSuccess {
                myActivityItemAdapter.update(UserActivityItemMapper.fromUserActivityList(it))
                return
            }
        myActivityItemAdapter.update(listOf())
    }

    /**
     * Game Collections
     */
    private fun setUpGameCollections() {
        with(binding) {
            val selectedTab =
                GameCollectionTab.fromGameCollectionType(viewModel.gameCollectionTypeState.value)
            tabLayoutCollections.getTabAt(selectedTab)?.select()
            recyclerGameCollection.adapter = gameCollectionAdapter
            recyclerGameCollection.addItemDecoration(
                HorizontalPaddingItemDecoration(
                    requireContext(),
                    R.dimen.spacing_small
                )
            )
        }
    }

    private fun onGameCollectionState(state: Outcome<List<Game>>) {
        state
            .onSuccess {
                gameCollectionItemAdapter.update(GameCoverItemMapper.fromGameList(it))
            }
    }

    /**
     * My activity
     */
    private fun setUpMyActivity() {
        with(binding) {
            recyclerMyActivity.adapter = myActivityAdapter
            recyclerMyActivity.addItemDecoration(
                VerticalPaddingItemDecoration(
                    requireContext(),
                    R.dimen.spacing_small
                )
            )
        }
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