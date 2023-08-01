package com.example.arcrecyclerview

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * An implementation of [ViewPagerLayoutManager]
 * which layouts item in a circle and will change the child's centerScale while scrolling
 */
class CircleScaleLayoutManager private constructor(
    context: Context?, radius: Int, angleInterval: Int, centerScale: Float,
    moveSpeed: Float, max: Float, min: Float, gravity: Int, zAlignment: Int,
    flipRotate: Boolean, maxVisibleItemCount: Int, distanceToBottom: Int, reverseLayout: Boolean
) : ViewPagerLayoutManager(context, HORIZONTAL, reverseLayout) {
    private var radius: Int
    private var angleInterval: Int
    private var moveSpeed: Float
    private var centerScale: Float
    private var maxRemoveAngle: Float
    private var minRemoveAngle: Float
    private var gravity: Int
    private var flipRotate: Boolean
    private var zAlignment: Int

    constructor(context: Context?) : this(Builder(context)) {}
    constructor(
        context: Context?,
        gravity: Int,
        reverseLayout: Boolean
    ) : this(Builder(context).setGravity(gravity).setReverseLayout(reverseLayout)) {
    }

    constructor(context: Context?, reverseLayout: Boolean) : this(
        Builder(context).setReverseLayout(
            reverseLayout
        )
    ) {
    }

    constructor(builder: Builder) : this(
        builder.context,
        builder.radius,
        builder.angleInterval,
        builder.centerScale,
        builder.moveSpeed,
        builder.maxRemoveAngle,
        builder.minRemoveAngle,
        builder.gravity,
        builder.zAlignment,
        builder.flipRotate,
        builder.maxVisibleItemCount,
        builder.distanceToBottom,
        builder.reverseLayout
    ) {
    }

    init {
        enableBringCenterToFront = true
        setMaxVisibleItemCount(maxVisibleItemCount)
        setDistanceToBottom(distanceToBottom)
        this.radius = radius
        this.angleInterval = angleInterval
        this.centerScale = centerScale
        this.moveSpeed = moveSpeed
        maxRemoveAngle = max
        minRemoveAngle = min
        this.gravity = gravity
        this.flipRotate = flipRotate
        this.zAlignment = zAlignment
    }

    fun getRadius(): Int {
        return radius
    }

    fun getAngleInterval(): Int {
        return angleInterval
    }

    fun getCenterScale(): Float {
        return centerScale
    }

    fun getMoveSpeed(): Float {
        return moveSpeed
    }

    fun getMaxRemoveAngle(): Float {
        return maxRemoveAngle
    }

    fun getMinRemoveAngle(): Float {
        return minRemoveAngle
    }

    fun getGravity(): Int {
        return gravity
    }

    fun getFlipRotate(): Boolean {
        return flipRotate
    }

    fun getZAlignment(): Int {
        return zAlignment
    }

    fun setRadius(radius: Int) {
        assertNotInLayoutOrScroll(null)
        if (this.radius == radius) return
        this.radius = radius
        removeAllViews()
    }

    fun setAngleInterval(angleInterval: Int) {
        assertNotInLayoutOrScroll(null)
        if (this.angleInterval == angleInterval) return
        this.angleInterval = angleInterval
        removeAllViews()
    }

    fun setCenterScale(centerScale: Float) {
        assertNotInLayoutOrScroll(null)
        if (this.centerScale == centerScale) return
        this.centerScale = centerScale
        requestLayout()
    }

    fun setMoveSpeed(moveSpeed: Float) {
        assertNotInLayoutOrScroll(null)
        if (this.moveSpeed == moveSpeed) return
        this.moveSpeed = moveSpeed
    }

    fun setMaxRemoveAngle(maxRemoveAngle: Float) {
        assertNotInLayoutOrScroll(null)
        if (this.maxRemoveAngle == maxRemoveAngle) return
        this.maxRemoveAngle = maxRemoveAngle
        requestLayout()
    }

    fun setMinRemoveAngle(minRemoveAngle: Float) {
        assertNotInLayoutOrScroll(null)
        if (this.minRemoveAngle == minRemoveAngle) return
        this.minRemoveAngle = minRemoveAngle
        requestLayout()
    }

    fun setGravity(gravity: Int) {
        assertNotInLayoutOrScroll(null)
        if (this.gravity == gravity) return
        this.gravity = gravity
        orientation =
            if (gravity == LEFT || gravity == RIGHT) {
                RecyclerView.VERTICAL
            } else {
                RecyclerView.HORIZONTAL
            }
        requestLayout()
    }

    fun setFlipRotate(flipRotate: Boolean) {
        assertNotInLayoutOrScroll(null)
        if (this.flipRotate == flipRotate) return
        this.flipRotate = flipRotate
        requestLayout()
    }

    fun setZAlignment(zAlignment: Int) {
        assertNotInLayoutOrScroll(null)
        if (this.zAlignment == zAlignment) return
        this.zAlignment = zAlignment
        requestLayout()
    }

    override fun setInterval(): Float {
        return angleInterval.toFloat()
    }

    override fun setUp() {
        radius = if (radius == Builder.INVALID_VALUE) mDecoratedMeasurementInOther else radius
    }

    override fun maxRemoveOffset(): Float {
        return maxRemoveAngle
    }

    override fun minRemoveOffset(): Float {
        return minRemoveAngle
    }

    override fun calItemLeft(itemView: View, targetOffset: Float): Int {
        return when (gravity) {
            LEFT -> (radius * Math.sin(
                Math.toRadians(
                    (90 - targetOffset).toDouble()
                )
            ) - radius).toInt()

            RIGHT -> (radius - radius * Math.sin(
                Math.toRadians(
                    (90 - targetOffset).toDouble()
                )
            )).toInt()

            TOP, BOTTOM -> (radius * Math.cos(
                Math.toRadians((90 - targetOffset).toDouble())
            )).toInt()

            else -> (radius * Math.cos(Math.toRadians((90 - targetOffset).toDouble()))).toInt()
        }
    }

    override fun calItemTop(itemView: View, targetOffset: Float): Int {
        return when (gravity) {
            LEFT, RIGHT -> (radius * Math.cos(
                Math.toRadians((90 - targetOffset).toDouble())
            )).toInt()

            TOP -> (radius * Math.sin(
                Math.toRadians(
                    (90 - targetOffset).toDouble()
                )
            ) - radius).toInt()

            BOTTOM -> (radius - radius * Math.sin(
                Math.toRadians(
                    (90 - targetOffset).toDouble()
                )
            )).toInt()

            else -> (radius - radius * Math.sin(Math.toRadians((90 - targetOffset).toDouble()))).toInt()
        }
    }

    override fun setItemViewProperty(itemView: View, targetOffset: Float) {
        var scale = 1f
        when (gravity) {
            RIGHT, TOP -> if (flipRotate) {
                itemView.rotation = targetOffset
                if (targetOffset < angleInterval && targetOffset > -angleInterval) {
                    val diff = Math.abs(Math.abs(itemView.rotation - angleInterval) - angleInterval)
                    scale = (centerScale - 1f) / -angleInterval * diff + centerScale
                }
            } else {
                itemView.rotation = 360 - targetOffset
                if (targetOffset < angleInterval && targetOffset > -angleInterval) {
                    val diff =
                        Math.abs(Math.abs(360 - itemView.rotation - angleInterval) - angleInterval)
                    scale = (centerScale - 1f) / -angleInterval * diff + centerScale
                }
            }

            LEFT, BOTTOM -> if (flipRotate) {
                itemView.rotation = 360 - targetOffset
                if (targetOffset < angleInterval && targetOffset > -angleInterval) {
                    val diff =
                        Math.abs(Math.abs(360 - itemView.rotation - angleInterval) - angleInterval)
                    scale = (centerScale - 1f) / -angleInterval * diff + centerScale
                }
            } else {
                itemView.rotation = targetOffset
                if (targetOffset < angleInterval && targetOffset > -angleInterval) {
                    val diff = Math.abs(Math.abs(itemView.rotation - angleInterval) - angleInterval)
                    scale = (centerScale - 1f) / -angleInterval * diff + centerScale
                }
            }

            else -> if (flipRotate) {
                itemView.rotation = 360 - targetOffset
                if (targetOffset < angleInterval && targetOffset > -angleInterval) {
                    val diff =
                        Math.abs(Math.abs(360 - itemView.rotation - angleInterval) - angleInterval)
                    scale = (centerScale - 1f) / -angleInterval * diff + centerScale
                }
            } else {
                itemView.rotation = targetOffset
                if (targetOffset < angleInterval && targetOffset > -angleInterval) {
                    val diff = Math.abs(Math.abs(itemView.rotation - angleInterval) - angleInterval)
                    scale = (centerScale - 1f) / -angleInterval * diff + centerScale
                }
            }
        }
        itemView.scaleX = scale
        itemView.scaleY = scale
    }

    fun onClickItem(view: View?) {
        focusView.translationY = 1.5f
    }

    override fun setViewElevation(itemView: View, targetOffset: Float): Float {
        return if (zAlignment == LEFT_ON_TOP) (540 - targetOffset) / 72 else if (zAlignment == RIGHT_ON_TOP) (targetOffset - 540) / 72 else (360 - Math.abs(
            targetOffset
        )) / 72
    }

    override fun getDistanceRatio(): Float {
        return if (moveSpeed == 0f) Float.MAX_VALUE else 1 / moveSpeed
    }

//    class Builder(val context: Context?) {
//        var radius: Int
//        var angleInterval: Int
//        var centerScale: Float
//        var moveSpeed: Float
//        var maxRemoveAngle: Float
//        var minRemoveAngle: Float
//        var reverseLayout: Boolean
//        var gravity: Int
//        var flipRotate: Boolean
//        var zAlignment: Int
//        var maxVisibleItemCount: Int
//        var distanceToBottom: Int
//
//        init {
//            radius = INVALID_VALUE
//            angleInterval = INTERVAL_ANGLE
//            centerScale = SCALE_RATE
//            moveSpeed = 1 / DISTANCE_RATIO
//            maxRemoveAngle = 90f
//            minRemoveAngle = -90f
//            reverseLayout = false
//            flipRotate = false
//            gravity = BOTTOM
//            zAlignment = CENTER_ON_TOP
//            distanceToBottom = INVALID_SIZE
//            maxVisibleItemCount = DETERMINE_BY_MAX_AND_MIN
//        }
//
//        fun setRadius(radius: Int): Builder {
//            this.radius = radius
//            return this
//        }
//
//        fun setAngleInterval(angleInterval: Int): Builder {
//            this.angleInterval = angleInterval
//            return this
//        }
//
//        fun setCenterScale(centerScale: Float): Builder {
//            this.centerScale = centerScale
//            return this
//        }
//
//        fun setMoveSpeed(moveSpeed: Int): Builder {
//            this.moveSpeed = moveSpeed.toFloat()
//            return this
//        }
//
//        fun setMaxRemoveAngle(maxRemoveAngle: Float): Builder {
//            this.maxRemoveAngle = maxRemoveAngle
//            return this
//        }
//
//        fun setMinRemoveAngle(minRemoveAngle: Float): Builder {
//            this.minRemoveAngle = minRemoveAngle
//            return this
//        }
//
//        fun setReverseLayout(reverseLayout: Boolean): Builder {
//            this.reverseLayout = reverseLayout
//            return this
//        }
//
//        fun setGravity(gravity: Int): Builder {
//            this.gravity = gravity
//            return this
//        }
//
//        fun setFlipRotate(flipRotate: Boolean): Builder {
//            this.flipRotate = flipRotate
//            return this
//        }
//
//        fun setZAlignment(zAlignment: Int): Builder {
//            this.zAlignment = zAlignment
//            return this
//        }
//
//        fun setMaxVisibleItemCount(maxVisibleItemCount: Int): Builder {
//            this.maxVisibleItemCount = maxVisibleItemCount
//            return this
//        }
//
//        fun setDistanceToBottom(distanceToBottom: Int): Builder {
//            this.distanceToBottom = distanceToBottom
//            return this
//        }
//
//        fun build(): CircleScaleLayoutManager {
//            return CircleScaleLayoutManager(this)
//        }
//
//        companion object {
//            private const val INTERVAL_ANGLE = 30 // The default mInterval angle between each items
//            private const val DISTANCE_RATIO = 10f // Finger swipe distance divide item rotate angle
//            private const val SCALE_RATE = 1.2f
//            const val INVALID_VALUE = Int.MIN_VALUE
//        }
//    }

    companion object {
        const val LEFT = 10
        const val RIGHT = 11
        const val TOP = 12
        const val BOTTOM = 13
        const val LEFT_ON_TOP = 4
        const val RIGHT_ON_TOP = 5
        const val CENTER_ON_TOP = 6

        class Builder(val context: Context?) {
            var radius: Int
            var angleInterval: Int
            var centerScale: Float
            var moveSpeed: Float
            var maxRemoveAngle: Float
            var minRemoveAngle: Float
            var reverseLayout: Boolean
            var gravity: Int
            var flipRotate: Boolean
            var zAlignment: Int
            var maxVisibleItemCount: Int
            var distanceToBottom: Int

            init {
                radius = INVALID_VALUE
                angleInterval = INTERVAL_ANGLE
                centerScale = SCALE_RATE
                moveSpeed = 1 / DISTANCE_RATIO
                maxRemoveAngle = 90f
                minRemoveAngle = -90f
                reverseLayout = false
                flipRotate = false
                gravity = BOTTOM
                zAlignment = CENTER_ON_TOP
                distanceToBottom = INVALID_SIZE
                maxVisibleItemCount = DETERMINE_BY_MAX_AND_MIN
            }

            fun setRadius(radius: Int): Builder {
                this.radius = radius
                return this
            }

            fun setAngleInterval(angleInterval: Int): Builder {
                this.angleInterval = angleInterval
                return this
            }

            fun setCenterScale(centerScale: Float): Builder {
                this.centerScale = centerScale
                return this
            }

            fun setMoveSpeed(moveSpeed: Int): Builder {
                this.moveSpeed = moveSpeed.toFloat()
                return this
            }

            fun setMaxRemoveAngle(maxRemoveAngle: Float): Builder {
                this.maxRemoveAngle = maxRemoveAngle
                return this
            }

            fun setMinRemoveAngle(minRemoveAngle: Float): Builder {
                this.minRemoveAngle = minRemoveAngle
                return this
            }

            fun setReverseLayout(reverseLayout: Boolean): Builder {
                this.reverseLayout = reverseLayout
                return this
            }

            fun setGravity(gravity: Int): Builder {
                this.gravity = gravity
                return this
            }

            fun setFlipRotate(flipRotate: Boolean): Builder {
                this.flipRotate = flipRotate
                return this
            }

            fun setZAlignment(zAlignment: Int): Builder {
                this.zAlignment = zAlignment
                return this
            }

            fun setMaxVisibleItemCount(maxVisibleItemCount: Int): Builder {
                this.maxVisibleItemCount = maxVisibleItemCount
                return this
            }

            fun setDistanceToBottom(distanceToBottom: Int): Builder {
                this.distanceToBottom = distanceToBottom
                return this
            }

//            fun build(): CircleScaleLayoutManager {
//                return CircleScaleLayoutManager(this)
//            }

            companion object {
                private const val INTERVAL_ANGLE = 30 // The default mInterval angle between each items
                private const val DISTANCE_RATIO = 10f // Finger swipe distance divide item rotate angle
                private const val SCALE_RATE = 1.2f
                const val INVALID_VALUE = Int.MIN_VALUE
            }
        }
    }
}