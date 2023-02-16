package com.app.cineverse.ui.movie

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


class MovieRvDivider(
    private val verticalSpaceHeight: Int = 0,
    private val horizontalSpaceHeight: Int = 0
) :
    ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.bottom = verticalSpaceHeight
        outRect.left = horizontalSpaceHeight

    }
}