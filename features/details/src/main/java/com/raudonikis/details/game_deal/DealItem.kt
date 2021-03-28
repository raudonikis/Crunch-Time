package com.raudonikis.details.game_deal

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.raudonikis.data_domain.game_deal.GameDeal
import com.raudonikis.details.R
import com.raudonikis.details.databinding.ItemDealBinding

data class DealItem(val deal: GameDeal) : AbstractBindingItem<ItemDealBinding>() {

    override val type: Int
        get() = R.id.adapter_deal_id

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ItemDealBinding {
        return ItemDealBinding.inflate(inflater, parent, false)
    }

    override fun bindView(binding: ItemDealBinding, payloads: List<Any>) {
        with(binding) {
            textDealShop.text = deal.shop.name
            textNewPrice.text = deal.priceNew.toString()
            textOldPrice.text = deal.priceOld.toString()
            //todo image
        }
    }
}
