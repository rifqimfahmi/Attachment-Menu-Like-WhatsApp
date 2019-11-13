package com.github.rifqimfahmi.softkeyboard

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.ceil

class MenuRecyclerView: RecyclerView {

    private val manager = GridLayoutManager(context, 4)
    private val adapter = GridMenuAdapter()
    private val marginBottom = 16 * Resources.getSystem().displayMetrics.density

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    init {
        setHasFixedSize(true)
        layoutManager = manager
        setAdapter(adapter)
        addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: State
            ) {
                val manager = parent.layoutManager as GridLayoutManager
                val spanCount = manager.spanCount
                val totalItem = manager.itemCount
                val lineCount = ceil(totalItem / spanCount.toDouble()).toInt()
                val noMarginLine = lineCount
                val position = parent.getChildLayoutPosition(view)

                if (position == NO_POSITION) super.getItemOffsets(outRect, view, parent, state)

                val currentLine = ceil((position + 1) / spanCount.toDouble()).toInt()
                if (currentLine != noMarginLine) {
                    outRect.bottom = marginBottom.toInt()
                }
            }
        })
    }

}