package com.raudonikis.details.screenshots

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.raudonikis.common.extensions.prefixHttps
import com.raudonikis.data_domain.screenshots.Screenshot
import com.raudonikis.details.R
import com.raudonikis.details.databinding.ItemScreenshotBinding

class ScreenshotItem(private val screenshot: Screenshot) :
    AbstractBindingItem<ItemScreenshotBinding>() {

    override val type: Int
        get() = R.id.adapter_screenshot_id

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ItemScreenshotBinding {
        return ItemScreenshotBinding.inflate(inflater, parent, false)
    }

    override fun bindView(binding: ItemScreenshotBinding, payloads: List<Any>) {
        with(binding) {
            Glide
                .with(root)
                .load(screenshot.url.prefixHttps())
                .centerCrop()
                .into(imageScreenshot)
        }
    }
}