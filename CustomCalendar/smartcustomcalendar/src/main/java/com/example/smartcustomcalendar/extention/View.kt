package com.example.customcalendar.extention

import android.graphics.Rect
import android.support.v4.view.ViewCompat
import android.view.TouchDelegate
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

inline fun View.visible(visible: Boolean = false) {
    visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

inline fun View.hide() {
    visibility = View.GONE
}

inline fun View.show() {
    visibility = View.VISIBLE
}

inline fun View.invisible() {
    visibility = View.INVISIBLE
}

inline fun View.isVisible() = visibility == View.VISIBLE

inline fun View.setMarginTop(margin: Int) {
    val marginLayout = layoutParams as? ViewGroup.MarginLayoutParams ?: return

    marginLayout.topMargin = margin
}

inline fun View.setMarginBottom(margin: Int) {
    val marginLayout = layoutParams as? ViewGroup.MarginLayoutParams ?: return

    marginLayout.bottomMargin = margin
}

inline fun View.setMarginLeft(margin: Int) {
    val marginLayout = layoutParams as? ViewGroup.MarginLayoutParams ?: return

    marginLayout.leftMargin = margin
}

inline fun View.setMarginRight(margin: Int) {
    val marginLayout = layoutParams as? ViewGroup.MarginLayoutParams ?: return

    marginLayout.rightMargin = margin
}

inline fun View.setWeight(weight: Float) {
    val params = layoutParams as? LinearLayout.LayoutParams ?: return
    params.weight = weight

    layoutParams = params
}


inline fun View.setHeight(height: Int) {
    layoutParams.height = height
}

fun View.getAllMargin(): Int {
    var parent: View? = this
    var margin = 0

    while (parent != null) {
        val layoutParams = parent.layoutParams

        if (layoutParams is ViewGroup.MarginLayoutParams) {

            margin += layoutParams.leftMargin
            margin += layoutParams.rightMargin
        }

        margin += parent.paddingLeft
        margin += parent.paddingRight

        parent = parent.parent as? View?
    }

    return margin
}


/**
 * Performs the given action when this view is next laid out.
 *
 * @see doOnLayout
 */
inline fun View.doOnNextLayout(crossinline action: (view: View) -> Unit) {
    addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
        override fun onLayoutChange(
                view: View, left: Int, top: Int, right: Int, bottom: Int,
                oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int
        ) {
            view.removeOnLayoutChangeListener(this)
            action(view)
        }
    })
}

/**
 * Performs the given action when this view is laid out. If the view has been laid out and it
 * has not requested a layout, the action will be performed straight away, otherwise the
 * action will be performed after the view is next laid out.
 *
 * @see doOnNextLayout
 */
inline fun View.doOnLayout(crossinline action: (view: View) -> Unit) {
    if (ViewCompat.isLaidOut(this) && !isLayoutRequested) {
        action(this)
    } else {
        doOnNextLayout {
            action(it)
        }
    }
}
