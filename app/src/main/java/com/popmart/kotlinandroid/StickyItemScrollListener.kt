package com.popmart.kotlinandroid

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.animation.DecelerateInterpolator

/**
 * 加动画效果
 * Created by mengxn on 2017/9/18.
 */
class StickyItemScrollListener : RecyclerView.OnScrollListener() {

    var lastPosition = 0

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val linearLayoutManager = recyclerView?.layoutManager as LinearLayoutManager
        val position = linearLayoutManager.findLastVisibleItemPosition()
        if (lastPosition == position) {
            return
        }
        // 向下滑动时，不支持动画
        if (lastPosition > position) {
            lastPosition = position
            return
        }
        lastPosition = position
        val holder = recyclerView.findViewHolderForAdapterPosition(position)
        holder.itemView.translationY = 100f
        val animate = holder.itemView.animate()
        val animator = animate.translationY(0f).setDuration(500).setStartDelay(200)
        animator.interpolator = DecelerateInterpolator()
        animate.start()
    }
}