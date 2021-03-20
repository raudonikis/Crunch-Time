package com.raudonikis.activity

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.raudonikis.activity.databinding.ItemUserActivityBinding
import com.raudonikis.data_domain.activity.models.UserActivity

class UserActivityItem(val userActivity: UserActivity) :
    AbstractBindingItem<ItemUserActivityBinding>() {

    override val type: Int
        get() = R.id.adapter_activity_id

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ItemUserActivityBinding {
        return ItemUserActivityBinding.inflate(inflater, parent, false)
    }

    override fun bindView(binding: ItemUserActivityBinding, payloads: List<Any>) {
        with(binding) {
            //todo
        }
    }
}