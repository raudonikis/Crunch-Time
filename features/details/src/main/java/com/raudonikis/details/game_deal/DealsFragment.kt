package com.raudonikis.details.game_deal

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.raudonikis.common_ui.extensions.update
import com.raudonikis.common_ui.item_decorations.VerticalPaddingItemDecoration
import com.raudonikis.data_domain.testDeals
import com.raudonikis.details.R
import com.raudonikis.details.databinding.FragmentDealsBinding
import com.raudonikis.details.game_deal.mappers.DealItemMapper
import com.wada811.viewbinding.viewBinding

class DealsFragment : Fragment(R.layout.fragment_deals) {

    private val binding: FragmentDealsBinding by viewBinding()

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
    }

    /**
     * Set up
     */
    private fun setUpDeals() {
        with(binding) {
            recyclerDeals.adapter = dealsAdapter
            recyclerDeals.addItemDecoration(
                VerticalPaddingItemDecoration(
                    requireContext(),
                    R.dimen.spacing_small
                )
            )
            dealsItemAdapter.update(DealItemMapper.fromDealList(testDeals))
        }
    }
}