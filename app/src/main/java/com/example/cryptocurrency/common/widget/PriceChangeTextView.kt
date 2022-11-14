package com.example.cryptocurrency.common.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.example.cryptocurrency.R

class PriceChangeTextView(
    context: Context,
    attrs: AttributeSet
) : AppCompatTextView(context, attrs) {

    var change: Double = 0.0
        set(value) {
            field = value
            updateComponents(value)
        }

    private fun updateComponents(change: Double) {
        if (change >= 0) {
            setTextColor(ContextCompat.getColor(context, R.color.green))
            setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_arrow_up,
                0,
                0,
                0
            )
        } else {
            setTextColor(ContextCompat.getColor(context, R.color.red))
            setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_arrow_down,
                0,
                0,
                0
            )
        }
        text = change.toString()
    }
}
