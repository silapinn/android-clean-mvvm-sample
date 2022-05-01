package com.example.cryptocurrency.common.widget.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

typealias EnumOrdinal = Int
private typealias HorizontalOffset = Int

class HorizontalMarginMultiItemDecoration(
    private val enumOrdinalToOffset: Map<EnumOrdinal, HorizontalOffset>
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val viewTypeOrdinal = parent.getChildViewHolder(view).itemViewType
        val margin: Int = enumOrdinalToOffset[viewTypeOrdinal] ?: 0
        outRect.left = margin
        outRect.right = margin
    }
}