package com.raudonikis.discover

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.raudonikis.common.extensions.asLiveData
import com.raudonikis.discover.databinding.FragmentDiscoverBinding
import com.wada811.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverFragment : Fragment(R.layout.fragment_discover) {

    private val viewModel: DiscoverViewModel by viewModels()
    private val binding: FragmentDiscoverBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textFieldSearch.editText?.setText(viewModel.searchQuery)
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.textFieldSearch.editText.asLiveData(lifecycleScope.coroutineContext)
            .observe(viewLifecycleOwner) {
                viewModel.search(it)
            }
    }
}