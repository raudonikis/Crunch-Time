package com.raudonikis.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.raudonikis.common.Outcome
import com.raudonikis.common_ui.extensions.observeInLifecycle
import com.raudonikis.common_ui.extensions.onClick
import com.raudonikis.common_ui.extensions.update
import com.raudonikis.common_ui.game_cover_item.GameCoverItem
import com.raudonikis.common_ui.game_cover_item.GameCoverItemMapper
import com.raudonikis.common_ui.item_decorations.HorizontalPaddingItemDecoration
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game.models.GameCollectionType
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
        setUpGameCollections()
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
            tabLayoutCollections.addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (tab?.position) {
                        0 -> viewModel.onGameCollectionTabSwitched(
                            GameCollectionType.PLAYED
                        )
                        1 -> viewModel.onGameCollectionTabSwitched(
                            GameCollectionType.PLAYING
                        )
                        2 -> viewModel.onGameCollectionTabSwitched(
                            GameCollectionType.WANT
                        )
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            })
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
        viewModel.gameCollection
            .onEach { onGameCollectionState(it) }
            .observeInLifecycle(viewLifecycleOwner)
    }

    /**
     * Game Collections
     */
    private fun setUpGameCollections() {
        with(binding) {
            val selectedTab = when (viewModel.gameCollectionTypeState.value) {
                GameCollectionType.PLAYED -> 0
                GameCollectionType.PLAYING -> 1
                GameCollectionType.WANT -> 2
            }
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