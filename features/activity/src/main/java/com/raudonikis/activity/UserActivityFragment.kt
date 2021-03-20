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
import com.raudonikis.common_ui.extensions.update
import com.raudonikis.common_ui.item_decorations.VerticalPaddingItemDecoration
import com.raudonikis.data_domain.testActivities
import com.wada811.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserActivityFragment : Fragment(R.layout.fragment_activity) {

    private val viewModel: UserActivityViewModel by viewModels()
    private val binding: FragmentActivityBinding by viewBinding()

    /**
     * Activity
     */
    private val activityItemAdapter = ItemAdapter<UserActivityItem>()
    private val activityAdapter = FastAdapter.with(activityItemAdapter)

    /**
     * Lifecycle hooks
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpActivity()
    }

    /**
     * Set up
     */
    private fun setUpActivity() {
        binding.apply {
            recyclerActivities.adapter = activityAdapter
            recyclerActivities.addItemDecoration(
                VerticalPaddingItemDecoration(
                    requireContext(),
                    R.dimen.spacing_small
                )
            )
        }
        activityItemAdapter.update(UserActivityItemMapper.fromUserActivityList(testActivities))
    }
}