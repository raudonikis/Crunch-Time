package com.raudonikis.details.game_deal

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.raudonikis.data_domain.game_deal.GameDeal
import com.raudonikis.details.R
import com.raudonikis.details.databinding.ItemDealBinding
import com.raudonikis.details.game_deal.mappers.GameDealShopIconMapper

data class DealItem(val deal: GameDeal) : AbstractBindingItem<ItemDealBinding>() {

    override val type: Int
        get() = R.id.adapter_deal_id

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ItemDealBinding {
        return ItemDealBinding.inflate(inflater, parent, false)
    }

    override fun bindView(binding: ItemDealBinding, payloads: List<Any>) {
        with(binding) {
            textDealShop.text = deal.shop.name
            textNewPrice.text = "€${deal.priceNew}"
            textOldPrice.apply {
                text = "€${deal.priceOld}"
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
            imageDealShop.contentDescription = deal.shop.name
            GameDealShopIconMapper.iconFromGameDealShop(deal.shop).let { iconId ->
                imageDealShop.setImageResource(iconId)
            }
        }
    }
}
