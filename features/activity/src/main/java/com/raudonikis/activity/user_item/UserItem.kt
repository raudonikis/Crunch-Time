package com.raudonikis.activity.user_item

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.raudonikis.activity.R
import com.raudonikis.activity.databinding.ItemUserBinding
import com.raudonikis.data_domain.user.User

data class UserItem(val user: User) : AbstractBindingItem<ItemUserBinding>() {

    override val type: Int
        get() = R.id.adapter_user_id

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ItemUserBinding {
        return ItemUserBinding.inflate(inflater, parent, false)
    }

    override fun bindView(binding: ItemUserBinding, payloads: List<Any>) {
        with(binding) {
            textUserName.text = user.name
            textUserEmail.text = user.email
        }
    }
}