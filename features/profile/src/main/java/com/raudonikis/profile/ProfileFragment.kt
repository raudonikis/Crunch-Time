package com.raudonikis.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.IAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.raudonikis.common.extensions.prefixHttps
import com.raudonikis.common_ui.HorizontalPaddingItemDecoration
import com.raudonikis.common_ui.RecyclerAdapter
import com.raudonikis.common_ui.databinding.ItemActivityBinding
import com.raudonikis.common_ui.extensions.observeInLifecycle
import com.raudonikis.data_domain.activity.models.UserActivity
import com.raudonikis.data_domain.game.models.Game
import com.raudonikis.data_domain.game.models.GameStatus
import com.raudonikis.profile.activity.ActivitiesState
import com.raudonikis.profile.collection.GameCoverItem
import com.raudonikis.profile.collection.mappers.GameCoverItemMapper
import com.raudonikis.profile.databinding.FragmentProfileBinding
import com.wada811.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by viewModels()
    private val binding: FragmentProfileBinding by viewBinding()
    private val gameCollectionItemAdapter = ItemAdapter<GameCoverItem>()
    private val gameCollectionAdapter = FastAdapter.with(gameCollectionItemAdapter)

    private val userActivityAdapter = RecyclerAdapter<UserActivity, ItemActivityBinding>(
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
    )

    /**
     * Lifecycle hooks
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
        setUpViews()
        setUpObservers()
        val games = listOf(
            Game(
                name = "Game 1",
                description = "Game 1 description",
                coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
                status = GameStatus.PLAYING,
            ),
            Game(
                name = "Game 2",
                description = "Game 2 description",
                coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
                status = GameStatus.PLAYED,
            ),
            Game(
                name = "Game 3",
                description = "Game 3 description",
                coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
                status = GameStatus.PLAYING,
            ),
            Game(
                name = "Game 4",
                description = "Game 4 description",
                coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
                status = GameStatus.PLAYING,
            ),
            Game(
                name = "Game 5",
                description = "Game 5 description",
                coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
                status = GameStatus.PLAYING,
            ),
            Game(
                name = "Game 6",
                description = "Game 6 description",
                coverUrl = "//images.igdb.com/igdb/image/upload/t_cover_big_2x/co2tvq.jpg",
                status = GameStatus.PLAYING,
            ),
        )
        gameCollectionItemAdapter.clear()
        gameCollectionItemAdapter.add(GameCoverItemMapper.fromGameList(games))
    }

    /**
     * Set up
     */
    private fun setUpViews() {
        with(binding) {
            recyclerActivity.apply {
                adapter = userActivityAdapter
                addItemDecoration(HorizontalPaddingItemDecoration(context, R.dimen.spacing_small))
            }
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
        gameCollectionAdapter.onClickListener =
            { _: View?, _: IAdapter<GameCoverItem>, gameCoverItem: GameCoverItem, _: Int ->
                viewModel.onGameClicked(gameCoverItem.game)
                false
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
                        userActivityAdapter.submitList(state.activities)
                    }
                    is ActivitiesState.Failure -> {

                    }
                }
            }
            .observeInLifecycle(viewLifecycleOwner)
    }
}