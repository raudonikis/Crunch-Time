package com.raudonikis.details.game_deal

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.raudonikis.common.Outcome
import com.raudonikis.common.extensions.showIf
import com.raudonikis.common_ui.extensions.observeInLifecycle
import com.raudonikis.common_ui.extensions.onClick
import com.raudonikis.common_ui.extensions.showLongSnackbar
import com.raudonikis.common_ui.extensions.update
import com.raudonikis.common_ui.item_decorations.VerticalPaddingItemDecoration
import com.raudonikis.data_domain.game_deal.GameDeal
import com.raudonikis.details.R
import com.raudonikis.details.databinding.FragmentDealsBinding
import com.raudonikis.details.game_deal.mappers.DealItemMapper
import com.wada811.viewbinding.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DealsFragment : Fragment(R.layout.fragment_deals) {

    private val viewModel: DealsViewModel by viewModels()
    private val binding: FragmentDealsBinding by viewBinding()
    private val args: DealsFragmentArgs by navArgs()

    /**
     * Deals
     */
    private val dealsItemAdapter = ItemAdapter<DealItem>()
    private val dealsAdapter = FastAdapter.with(dealsItemAdapter)

    /**
     * Lifecycle hooks
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpDeals()
        setUpObservers()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onCreate(args.game)
    }

    /**
     * Set up
     */
    private fun setUpDeals() {
        with(binding) {
            recyclerDeals.adapter = dealsAdapter.apply {
                onClick {
                    viewModel.onDealClicked(it.deal)
                }
            }
            recyclerDeals.addItemDecoration(
                VerticalPaddingItemDecoration(
                    requireContext(),
                    R.dimen.spacing_normal
                )
            )
        }
    }

    private fun setUpObservers() {
        viewModel.dealsState
            .onEach { onDealsState(it) }
            .observeInLifecycle(viewLifecycleOwner)
        viewModel.errorEvent
            .onEach { onErrorEvent(it) }
            .observeInLifecycle(viewLifecycleOwner)
    }

    /**
     * Events
     */
    private fun onDealsState(state: Outcome<List<GameDeal>>) {
        with(binding) {
            groupLoading.showIf { state is Outcome.Loading }
            groupFailure.showIf { state is Outcome.Failure }
            recyclerDeals.showIf { state is Outcome.Success }
            state
                .onSuccess { deals ->
                    DealItemMapper.fromDealList(deals).sortedBy { it.deal.priceNew }.also {
                        dealsItemAdapter.update(it)
                    }
                    binding.groupFailure.showIf { deals.isEmpty() }
                }
        }
    }

    private fun onErrorEvent(error: String) {
        showLongSnackbar(error)
    }
}