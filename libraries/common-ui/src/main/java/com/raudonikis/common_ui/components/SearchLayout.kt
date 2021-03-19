package com.raudonikis.common_ui.components

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.raudonikis.common.extensions.asFlow
import com.raudonikis.common_ui.R
import com.raudonikis.common_ui.databinding.LayoutSearchBinding
import kotlinx.coroutines.flow.Flow

class SearchLayout(context: Context, attributeSet: AttributeSet) :
    FrameLayout(context, attributeSet) {

    private val binding: LayoutSearchBinding

    init {
        val view = inflate(context, R.layout.layout_search, this)
        binding = LayoutSearchBinding.bind(view)
    }

    fun asFlow(): Flow<String> {
        return binding.searchField.editText.asFlow()
    }

    fun setSearchQuery(query: String) {
        binding.searchField.editText?.setText(query)
    }

    fun setOnClearClick(onClick: () -> Unit) {
        binding.searchField.setEndIconOnClickListener {
            binding.searchField.editText?.setText("")
            onClick.invoke()
        }
    }
}