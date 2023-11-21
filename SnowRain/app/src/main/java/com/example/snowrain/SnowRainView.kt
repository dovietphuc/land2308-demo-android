package com.example.snowrain

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View

class SnowRainView : View {
    val backgroundColor = Color.BLACK
    val snowBitmap = BitmapFactory.decodeResource(resources, R.drawable.snow)
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val snowList = ArrayList<Snow>()
    val velocity = 200f
    val animator = ValueAnimator.ofFloat(0f, 2400f).apply {
        addUpdateListener {anim ->
            duration = 50
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
            snowList.forEach {
                if(it.y > 2400) {
                    it.random()
                }
                it.y += (velocity * it.scale).toInt()
            }
            invalidate()
        }
    }

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        for(i in 0 until 200) {
            snowList.add(Snow(MeasureSpec.getSize(widthMeasureSpec),
                MeasureSpec.getSize(heightMeasureSpec)))
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        animator.start()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            paint.color = backgroundColor
            canvas.drawRect(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), paint)

            snowList.forEach {snow ->
                val snowRect = Rect(
                    snow.x, snow.y,
                    snow.x + (snowBitmap.width * snow.scale).toInt()
                    , snow.y + (snowBitmap.height * snow.scale).toInt()
                )
                canvas.drawBitmap(snowBitmap, null, snowRect, paint)
            }
        }
    }
}