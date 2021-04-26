package com.raudonikis.common_ui.item_decorations

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

class HorizontalPaddingItemDecoration(private val size: Int) : RecyclerView.ItemDecoration() {

    constructor(
        context: Context,
        @DimenRes sizeDimenRes: Int
    ) : this(context.resources.getDimensionPixelSize(sizeDimenRes))

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        // Don't apply offsets to the first item
        if (parent.getChildAdapterPosition(view) == 0) {
            return
        }
        outRect.left += size
    }
}