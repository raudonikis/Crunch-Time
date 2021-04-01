package com.raudonikis.common_ui.extensions

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.GenericItem
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.listeners.ClickEventHook

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

fun <ITEM : GenericItem> FastAdapter<ITEM>.onViewClick(@IdRes viewId: Int, f: (ITEM) -> Unit) {
    addEventHook(object : ClickEventHook<ITEM>() {

        override fun onBind(viewHolder: RecyclerView.ViewHolder): View? {
            return viewHolder.itemView.findViewById(viewId)
        }

        override fun onClick(
            v: View,
            position: Int,
            fastAdapter: FastAdapter<ITEM>,
            item: ITEM
        ) {
            f(item)
        }
    })
}