package com.example.cryptocurrency.common

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ScrollEndListener(val onScrollEnd: () -> Unit) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (dy > 0) {
            val layoutManager = recyclerView.layoutManager
            if (layoutManager is LinearLayoutManager) {
                val visibleItemCount = layoutManager.childCount;
                val totalItemCount = layoutManager.itemCount;
                val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()

                if ((visibleItemCount + firstVisiblePosition) >= totalItemCount) {
                    onScrollEnd.invoke()
                }
            }
        }

    }
}