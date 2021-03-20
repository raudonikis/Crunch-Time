package com.raudonikis.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.raudonikis.common_ui.item_decorations.HorizontalPaddingItemDecoration
import com.raudonikis.common_ui.extensions.observeInLifecycle
import com.raudonikis.common_ui.extensions.onClick
import com.raudonikis.common_ui.extensions.update
import com.raudonikis.profile.activity.ActivitiesState
import com.raudonikis.common_ui.game_cover.GameItem
import com.raudonikis.common_ui.game_cover.GameCoverItemMapper
import com.raudonikis.data_domain.testGames
import com.raudonikis.profile.databinding.FragmentProfileBinding
import com.wada811.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by viewModels()
    private val binding: FragmentProfileBinding by viewBinding()
    private val gameCollectionItemAdapter = ItemAdapter<GameItem>()
    private val gameCollectionAdapter = FastAdapter.with(gameCollectionItemAdapter)

    /*private val userActivityAdapter = RecyclerAdapter<UserActivity, ItemActivityBinding>(
        onInflate = { inflater, parent ->
            ItemActivityBinding.inflate(inflater, parent, false)
        },
        onBind = { activity ->
            activity.coverUrl?.let { url ->
                Glide
                    .with(root)
                    .load(url.prefixHttps())
                    .placeholder(R.drawable.game_placeholder)
                    .centerCrop()
                    .into(activityImage)
            }
        },
        onClick = { viewModel.onUserActivityClick(this) }
    )*/

    /**
     * Lifecycle hooks
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
        setUpViews()
        setUpObservers()
        val games = GameCoverItemMapper.fromGameList(testGames)
        gameCollectionItemAdapter.update(games)
    }

    /**
     * Set up
     */
    private fun setUpViews() {
        with(binding) {
            /*recyclerActivity.apply {
                adapter = userActivityAdapter
                addItemDecoration(HorizontalPaddingItemDecoration(context, R.dimen.spacing_small))
            }*/
            recyclerGameCollection.apply {
                adapter = gameCollectionAdapter
                addItemDecoration(HorizontalPaddingItemDecoration(context, R.dimen.spacing_small))
            }
        }
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
            .onEach { state ->
                when (state) {
                    is ActivitiesState.Initial -> {

                    }
                    is ActivitiesState.Loading -> {

                    }
                    is ActivitiesState.Success -> {
//                        userActivityAdapter.submitList(state.activities)
                    }
                    is ActivitiesState.Failure -> {

                    }
                }
            }
            .observeInLifecycle(viewLifecycleOwner)
    }
}