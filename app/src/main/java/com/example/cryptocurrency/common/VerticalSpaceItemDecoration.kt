package com.example.cryptocurrency.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

private typealias VerticalOffset = Int

class VerticalSpaceItemDecoration(
    private val enumOrdinalToOffset: Map<EnumOrdinal, VerticalOffset>
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val viewTypeOrdinal = parent.getChildViewHolder(view).itemViewType
        val space: Int = enumOrdinalToOffset[viewTypeOrdinal] ?: 0
        outRect.bottom = space
    }
}