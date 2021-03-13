package com.raudonikis.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.raudonikis.common.extensions.prefixHttps
import com.raudonikis.common_ui.RecyclerAdapter
import com.raudonikis.common_ui.databinding.ItemActivityBinding
import com.raudonikis.common_ui.extensions.observeInLifecycle
import com.raudonikis.data_domain.activity.models.UserActivity
import com.raudonikis.data_domain.game.models.GameStatus
import com.raudonikis.profile.activity.ActivitiesState
import com.raudonikis.profile.databinding.FragmentProfileBinding
import com.wada811.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by viewModels()
    private val binding: FragmentProfileBinding by viewBinding()

    private val userActivityAdapter = RecyclerAdapter<UserActivity, ItemActivityBinding>(
        onInflate = { inflater, parent ->
            ItemActivityBinding.inflate(inflater, parent, false)
        },
        onBind = { activity ->
            activity.coverUrl?.let { url ->
                Glide
                    .with(root)
                    .load(url.prefixHttps())
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
    }

    /**
     * Set up
     */
    private fun setUpViews() {
        with(binding) {
            recyclerActivity.adapter = userActivityAdapter
        }
    }

    /**
     * Listeners
     */
    private fun setUpListeners() {
        with(binding) {
            cardPlayed.setOnClickListener {
                viewModel.onCollectionClicked(GameStatus.PLAYED)
            }
            cardPlaying.setOnClickListener {
                viewModel.onCollectionClicked(GameStatus.PLAYING)
            }
            cardWant.setOnClickListener {
                viewModel.onCollectionClicked(GameStatus.WANT)
            }
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