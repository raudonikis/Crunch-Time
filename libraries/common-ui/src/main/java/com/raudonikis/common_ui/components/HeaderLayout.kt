package com.raudonikis.common_ui.components

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.LinearLayout
import com.raudonikis.common.extensions.showIf
import com.raudonikis.common_ui.R
import com.raudonikis.common_ui.databinding.LayoutHeaderBinding

class HeaderLayout(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet) {

    private val binding: LayoutHeaderBinding

    init {
        val view = inflate(context, R.layout.layout_header, this)
        binding = LayoutHeaderBinding.bind(view)
        val attributes =
            context.obtainStyledAttributes(attributeSet, R.styleable.HeaderLayout)
        setAttributes(attributes)
        attributes.recycle()
    }

    private fun setAttributes(attributes: TypedArray) {
        val titleText = attributes.getString(R.styleable.HeaderLayout_header_title)
        val isSearchEnabled =
            attributes.getBoolean(R.styleable.HeaderLayout_header_search_enabled, false)
        val searchHintText = attributes.getString(R.styleable.HeaderLayout_header_search_hint)
        setTitle(titleText)
        setSearchHint(searchHintText)
        enableSearch(isSearchEnabled)
    }

    fun setTitle(title: String?) {
        binding.textTitle.text = title
    }

    private fun setSearchHint(hint: String?) {
        binding.layoutSearch.setSearchHint(hint)
    }

    private fun enableSearch(isEnabled: Boolean) {
        binding.layoutSearch.showIf { isEnabled }
    }

    fun getSearchComponent(): SearchLayout {
        return binding.layoutSearch
    }
}