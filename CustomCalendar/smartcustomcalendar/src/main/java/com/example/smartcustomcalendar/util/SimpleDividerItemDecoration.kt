package com.example.smartcustomcalendar.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View

import com.example.smartcustomcalendar.R

/**
 * Created by iagomendesfucolo on 24/03/17.
 */

class SimpleDividerItemDecoration(context: Context, drawable: Int = R.drawable.line_divider) : RecyclerView.ItemDecoration() {

    private val mDivider: Drawable = context.resources.getDrawable(drawable)

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider.intrinsicHeight

            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
        }
    }
}
