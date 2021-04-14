package com.raudonikis.discover.components

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.GenericItem
import com.raudonikis.common_ui.item_decorations.HorizontalPaddingItemDecoration
import com.raudonikis.discover.R
import com.raudonikis.discover.databinding.LayoutHorizontalGameListBinding

class HorizontalGameList(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet) {

    private val binding: LayoutHorizontalGameListBinding

    init {
        val view = inflate(context, R.layout.layout_horizontal_game_list, this)
        binding = LayoutHorizontalGameListBinding.bind(view)
        val attributes =
            context.obtainStyledAttributes(attributeSet, R.styleable.HorizontalGameList)
        setAttributes(attributes)
        attributes.recycle()
        setUp()
    }

    private fun setUp() {
        binding.recyclerGames.apply {
            addItemDecoration(HorizontalPaddingItemDecoration(context, R.dimen.spacing_small))
        }
    }

    private fun setAttributes(attributes: TypedArray) {
        val titleText = attributes.getString(R.styleable.HorizontalGameList_hgl_title)
        setTitle(titleText)
    }

    fun <T : GenericItem> setAdapter(adapter: FastAdapter<T>) {
        binding.recyclerGames.adapter = adapter
    }

    fun setTitle(title: String?) {
        binding.title.text = title
    }

    fun onScrollStateChanged(listener: (newState: Int) -> Unit) {
        binding.recyclerGames.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    listener((binding.recyclerGames.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition())
                }
            }
        })
    }

    fun setScrollState(scrollState: Int) {
        binding.recyclerGames.scrollToPosition(scrollState)
    }
}