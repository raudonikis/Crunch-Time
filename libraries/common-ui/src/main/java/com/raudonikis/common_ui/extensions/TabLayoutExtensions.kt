package com.raudonikis.common_ui.extensions

import com.google.android.material.tabs.TabLayout

fun TabLayout.onTabSelected(listener: (position: Int) -> Unit) {
    addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            listener(tab.position)
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
        }

        override fun onTabReselected(tab: TabLayout.Tab?) {
        }
    })
}