package com.raudonikis.activity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.raudonikis.activity.databinding.FragmentActivityBinding
import com.raudonikis.activity.user_activity_item.UserActivityItem
import com.raudonikis.activity.user_activity_item.UserActivityItemMapper
import com.raudonikis.common.Outcome
import com.raudonikis.common_ui.extensions.observeInLifecycle
import com.raudonikis.common_ui.extensions.showShortSnackbar
import com.raudonikis.common_ui.extensions.update
import com.raudonikis.common_ui.item_decorations.VerticalPaddingItemDecoration
import com.raudonikis.data_domain.activity.models.UserActivity
import com.wada811.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
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
     * Lifecycle hooks
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpNewsFeed()
        setUpObservers()
    }

    /**
     * Set up
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

    private fun setUpObservers() {
        viewModel.newsFeedState
            .onEach { updateNewsFeedState(it) }
            .observeInLifecycle(viewLifecycleOwner)
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

}