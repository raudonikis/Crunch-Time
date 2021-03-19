package com.raudonikis.common_ui.extensions

import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.GenericItem
import com.mikepenz.fastadapter.adapters.ItemAdapter

fun <ITEM : GenericItem> FastAdapter<ITEM>.onClick(f: (ITEM) -> Unit) {
    this.onClickListener = { _, _, item, _ ->
        f(item)
        false
    }
}

fun <ITEM : GenericItem> ItemAdapter<ITEM>.update(items: List<ITEM>) {
    this.clear()
    this.add(items)
}