package com.raudonikis.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.raudonikis.activity.databinding.FragmentActivityBinding
import com.raudonikis.activity.user_activity_item.UserActivityItem
import com.raudonikis.activity.user_activity_item.UserActivityItemMapper
import com.raudonikis.common.extensions.Outcome
import com.raudonikis.common_ui.extensions.observeInLifecycle
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
     * Activity
     */
    private val userActivityItemAdapter = ItemAdapter<UserActivityItem>()
    private val userActivityAdapter = FastAdapter.with(userActivityItemAdapter)

    /**
     * Lifecycle hooks
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUserActivity()
        setUpObservers()
    }

    /**
     * Set up
     */
    private fun setUpUserActivity() {
        binding.apply {
            recyclerActivities.adapter = userActivityAdapter
            recyclerActivities.addItemDecoration(
                VerticalPaddingItemDecoration(
                    requireContext(),
                    R.dimen.spacing_small
                )
            )
        }
    }

    private fun setUpObservers() {
        viewModel.userActivityState
            .onEach { updateUserActivityState(it) }
            .observeInLifecycle(viewLifecycleOwner)
    }

    private fun updateUserActivityState(state: Outcome<List<UserActivity>>) {
        state
            .onSuccess {
                userActivityItemAdapter.update(UserActivityItemMapper.fromUserActivityList(it))
            }
            .onFailure {
                Toast.makeText(requireContext(), "Failure", Toast.LENGTH_SHORT).show()}
            .onLoading {
                Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
            }
            .onEmpty {
                Toast.makeText(requireContext(), "Empty", Toast.LENGTH_SHORT).show() }
    }

}