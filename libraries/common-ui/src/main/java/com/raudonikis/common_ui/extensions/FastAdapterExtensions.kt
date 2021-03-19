package com.raudonikis.common_ui.extensions

import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.GenericItem

fun <ITEM : GenericItem> FastAdapter<ITEM>.onClick(f: (ITEM) -> Unit) {
    this.onClickListener = { _, _, item, _ ->
        f(item)
        false
    }
}