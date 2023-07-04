package com.example.arcrecyclerview

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.arcrecyclerview.DisplayExt.toDp
import com.example.arcrecyclerview.DisplayExt.toPx
import java.lang.Math.abs
import kotlin.math.PI
import kotlin.math.acos
import kotlin.math.sin

class HorizontalArchLayoutManager(
    context: Context,
    private var horizontalOffset: Int = 0
) : LinearLayoutManager(context, HORIZONTAL, false) {
    private val screenWidth = context.resources.displayMetrics.widthPixels.toDouble()
    private val itemWidth = 100.toPx()
    private val itemHeight = 200.toPx()
    private var selectedPosition = -1
    private val initialItemLeftPoint = (screenWidth / 2) - (itemWidth / 2)  // 첫번째 아이템 left position
    private val lastItemVisibleLeft =
        initialItemLeftPoint                  // 마지막 아이템의 최소 left point
    private val firstItemVisibleLeft =
        initialItemLeftPoint                 // 첫번째 아이템의 최대 left point
    private val centerXPoint =
        (initialItemLeftPoint + itemWidth) / 2.toDouble()    // 스크린 중앙점이자 첫 생성 뷰의 중앙점

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams =
        RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )


    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        fill(recycler, state)
    }


    private fun fill(recycler: RecyclerView.Recycler?, state: RecyclerView.State, dx: Int = 0) {
        detachAndScrapAttachedViews(recycler ?: return)

        val lastItem = recycler.getViewForPosition(state.itemCount - 1)
        val firstItem = recycler.getViewForPosition(0)

        val movableToLeftPx = lastItem.left - lastItemVisibleLeft
        val movableToRightPx = firstItemVisibleLeft - firstItem.left


        horizontalOffset += if (dx > 0 && movableToLeftPx < dx) {
            movableToLeftPx.toInt()
        } else if (dx < 0 && movableToRightPx < kotlin.math.abs(dx)) {
            movableToRightPx.toInt()
        } else {
            dx
        }

        for (itemIndex in 0 until itemCount) {

            val view = recycler.getViewForPosition(itemIndex)
            addView(view)


            val leftPoint = (screenWidth / 2) - (itemWidth / 2) + (75.toPx() * itemIndex)

            val left = leftPoint - horizontalOffset
            val right = left + itemWidth
            val top = computeYComponent((left + right) / 2.toDouble())
            val bottom = top.first + itemHeight
            val alpha = top.second
            view.rotation = (alpha * (180 / PI)).toFloat() - 90f
            measureChildWithMargins(view, view.width, itemHeight)
            layoutDecoratedWithMargins(
                view,
                left.toInt(),
                top.first,
                right.toInt(),
                bottom
            )
        }

        recycler.scrapList.toList().forEach {
            recycler.recycleView(it.itemView)
        }

    }

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State
    ): Int {
        fill(recycler, state, dx)
        return dx
    }

    private fun computeYComponent(
        centerX: Double
    ): Pair<Int, Double> {
        val halfWidth = screenWidth / 2
        val radius = halfWidth * 2

        val xScreenFraction = centerX / screenWidth

        val beta = acos(halfWidth / radius)

        val alpha = beta + (xScreenFraction * (Math.PI - (2 * beta)))
        val yComponent = radius - (radius * sin(alpha)) - 20.toDp()

        return Pair(yComponent.toInt(), alpha)
    }

    override fun canScrollHorizontally(): Boolean = true
}