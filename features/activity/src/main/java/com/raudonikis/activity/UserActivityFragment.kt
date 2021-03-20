package com.raudonikis.activity

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.raudonikis.activity.databinding.FragmentActivityBinding
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
}