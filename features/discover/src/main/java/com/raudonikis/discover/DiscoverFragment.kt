package com.raudonikis.discover

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.raudonikis.common.extensions.asFlow
import com.raudonikis.discover.databinding.FragmentDiscoverBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DiscoverFragment : Fragment(R.layout.fragment_discover) {

    private val viewModel: DiscoverViewModel by activityViewModels()
    private var binding: FragmentDiscoverBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDiscoverBinding.bind(view).apply {
            textFieldSearch.editText?.setText(viewModel.searchQuery)
        }
        setUpListeners()
    }

    private fun setUpListeners() {
        binding?.apply {
            lifecycleScope.launch(Dispatchers.IO) {
                textFieldSearch.editText.asFlow().debounce(800)
                    .collect {
                        viewModel.search(it)
                    }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}