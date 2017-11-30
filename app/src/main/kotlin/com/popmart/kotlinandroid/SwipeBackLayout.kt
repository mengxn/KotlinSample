package com.popmart.kotlinandroid

import android.content.Context
import android.support.v4.widget.ViewDragHelper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout

/**
 * Created by mengxn on 2017/11/30.
 */
class SwipeBackLayout(context: Context, attributeSet: AttributeSet? = null, var layoutId: Int = 0) : FrameLayout(context, attributeSet) {

    private lateinit var dragHelper: ViewDragHelper
    private lateinit var dragView: View
    private var currentX = 0
    private var currentEdgeFlag = 0

    companion object {

        fun with(context: Context, resId: Int): SwipeBackLayout {
            return SwipeBackLayout(context, layoutId = resId)
        }
    }

    constructor(context: Context, attributeSet: AttributeSet? = null): this(context, attributeSet, 0)

    init {
        layoutId.takeIf { it > 0 }?.let {
            View.inflate(context, layoutId, this)
            dragView = getChildAt(0)
        }

        dragHelper = ViewDragHelper.create(this, 1f, object : ViewDragHelper.Callback() {
            override fun tryCaptureView(child: View?, pointerId: Int): Boolean = false

            override fun onEdgeDragStarted(edgeFlags: Int, pointerId: Int) {
                super.onEdgeDragStarted(edgeFlags, pointerId)
                currentEdgeFlag = edgeFlags
                dragHelper.captureChildView(dragView, pointerId)
            }

            override fun clampViewPositionHorizontal(child: View?, left: Int, dx: Int): Int =
                    Math.max(left, 0).also { currentX = it }

            override fun onViewReleased(releasedChild: View?, xvel: Float, yvel: Float) {
                super.onViewReleased(releasedChild, xvel, yvel)
                if (currentEdgeFlag == ViewDragHelper.EDGE_LEFT) {
                    (if (currentX > width / 2) width else 0).let {
                        dragHelper.settleCapturedViewAt(it, 0)
                    }.also {
                        currentX = 0
                        invalidate()
                    }
                }
            }

            override fun onViewPositionChanged(changedView: View?, left: Int, top: Int, dx: Int, dy: Int) {
                super.onViewPositionChanged(changedView, left, top, dx, dy)
                if (currentEdgeFlag == ViewDragHelper.EDGE_LEFT) {
                    (left.toFloat() / width).let {
                        if (it >= 1) swipeCallback?.onFinish() else swipeCallback?.onSwipe(it)
                    }
                }
            }
        })
        dragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount == 1) {
            dragView = getChildAt(0)
        } else {
            throw IllegalStateException("wrap one child view")
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return dragHelper.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        dragHelper.processTouchEvent(event)
        return true
    }

    override fun computeScroll() {
        super.computeScroll()
        if (dragHelper.continueSettling(true)) {
            invalidate()
        }
    }

    interface Callback{

        fun onSwipe(percent: Float){}

        fun onFinish()

    }

    private var swipeCallback: Callback? = null

    fun setCallback(callback: Callback?) {
        swipeCallback = callback
    }

}